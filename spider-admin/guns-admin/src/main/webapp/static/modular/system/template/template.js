/**
 * 模板管理
 */
var TemplateMgr = {
    delimiters: ',; \n\t'
};

/**
 * 查询模板列表
 */
TemplateMgr.getTemplates = function (successCallback) {
    var ajax = new $ax(Feng.ctxPath + '/template/list',function (data) {
        if(data && data.length > 0){
            var ary = [];
            $.each(data,function (i, item) {
                var $li = $('<a href="javascript:;" class="list-group-item" data-templateid="'+item.id+'">'+item.name+'</a>');
                ary.push($li);
            });

            var $tempList = $('#template_list')
            $tempList.empty().append(ary);

            if(successCallback) {
                successCallback(data);
            }
            //selectTemplate(ary[0]);
        }
    });
    ajax.type = 'get';
    ajax.start();
}

/**
 * 选中某个模板
 */
TemplateMgr.selectTemplate = function (templateId) {
    var $tempList = $('#template_list');
    $tempList.find('a').removeClass('active');

    $tempList.find('a[data-templateid="'+templateId+'"]').addClass("active");

    $('#accordion').empty();
    TemplateMgr.getAndFillTemplate(templateId);
};

/**
 * 获取模板信息并进行填充
 * @param templateId
 */
TemplateMgr.getAndFillTemplate = function (templateId) {
    var ajax = new $ax(Feng.ctxPath + '/template/' + templateId,function (data) {
        TemplateMgr.fillData(data);
    });
    ajax.type = 'get';
    ajax.start();
};

/**
 * 填充模板信息
 * @param data
 */
TemplateMgr.fillData = function (data) {
    console.log(JSON.stringify(data));
    if(!data) {
        return;
    }

    $('#id').val(data.id);
    $('#name').val(data.name);
    $('#seedUrl').val(data.seedUrl.url);

    //==============url正则表达式解析 start=============
    var urlRegex = data.urlRegex;
    if(urlRegex) {
        $('#urlRegex').val(urlRegex.join('\n'));
    }

    //==============区域规则解析 start=============
    var regionRules = data.regionRules;
    if(regionRules && regionRules.length > 0) {
        $.each(regionRules,function(i,regionRule){
            TemplateMgr.appendRegionRule(regionRule.name, regionRule);
        });
    }

};

/**
 * 重置模板信息
 */
TemplateMgr.reset = function () {
    $('input,textarea').val('');

    $('#accordion').empty();
    $('#regionTip').show();

    //取消选中的模板
    $('#template_list a').removeClass('active');
};

TemplateMgr.save = function () {
    if(this.check()){
        var data = this.fetchData();
        console.log(JSON.stringify(data));

        var ajax = new $ax(Feng.ctxPath + '/template/upsert',function (data) {
            if(data.success){
                Feng.success("保存成功");
            }
        });
        ajax.contentType = "text/plain";
        ajax.setData(JSON.stringify(data));
        ajax.start();
    }
};

TemplateMgr.fetchData = function () {
    var data = {
        id: $('#id').val() || null,
        name:  $('#name').val(),
        seedUrl: {
            url: $('#seedUrl').val()
        }
    };

    var urlRegexStr = $('#urlRegex').val();
    data.urlRegex = Feng.tokenizeToStringArray(urlRegexStr,TemplateMgr.delimiters);

    data.regionRules = this.fetchRegionRuleData();

    return data;
};

/**
 * 获取区域规则信息
 */
TemplateMgr.fetchRegionRuleData = function () {
    var regionRules = [];
    var zoneList = $('#accordion > .panel');
    if(zoneList.length > 0) {
        $.each(zoneList,function(i,zone){
            var $zone = $(zone);
            var regionName = $.trim($zone.children('.panel-heading').find('.panel-title').text()),
                zoneUrlRegex = $zone.find('input[name="zoneUrlRegex"]').val(),
                zoneExtractContext = $zone.find('input[name="zoneExtractContext"]').val();

            var fieldRuleData = TemplateMgr.fetchFieldRuleData($zone.find('[role="fieldRuleRegion"]'));

            regionRules.push({
                name: regionName,
                selectExpression: zoneExtractContext,
                urlRegex: zoneUrlRegex,
                fieldRules: fieldRuleData
            });
        });
    }

    return regionRules;
};

/**
 * 获取字段抽取规则
 * @param $fieldRuleRegion
 */
TemplateMgr.fetchFieldRuleData = function ($fieldRuleRegion) {
    var fieldRules = [];

    var fieldList = $fieldRuleRegion.find('.fieldRuleContainer');
    if(fieldList.length > 0) {
        $.each(fieldList,function (i, item) {
            var $fieldRegion = $(item);
            var fieldName = $fieldRegion.find('input[name="fieldName"]').val(),
                rule = $fieldRegion.find('input[name="rule"]').val();

            fieldRules.push({
                fieldName: fieldName,
                rule: rule
            });
        });
    }

    return fieldRules;
};

TemplateMgr.check = function () {
   /* var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MgrUser.seItem = selected[0];
        return true;
    }*/

   return true;
};

/**
 * 输入区域规则的名称
 */
TemplateMgr.enterRegionRuleName = function () {
    layer.prompt({
        title: '请输入区域规则名称',
    }, function(value, index, elem){
        layer.close(index);

        TemplateMgr.appendRegionRule(value);
    });
};

/**
 * 添加区域规则 html
 */
TemplateMgr.appendRegionRule = function (regionName,ruleData) {
    var panels = $('#accordion > .panel');
    var curIndex = panels.length;
    var html = '<div class="panel panel-default" id="'+TemplateMgr.buildRegionId(curIndex)+'">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="panel-heading" role="tab" id="headingOne_'+curIndex+'">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h4 class="panel-title">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_'+curIndex+'" aria-expanded="true" aria-controls="collapseOne_'+curIndex+'">\n' +
                                            regionName +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</h4>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class="fa fa-times-circle heading-font-btn" role="delete" onclick="TemplateMgr.deleteRegionRule('+curIndex+')"></i>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id="collapseOne_'+curIndex+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne_'+curIndex+'">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="panel-body">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="form-group">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label for="zoneUrlRegex_'+curIndex+'" class="col-sm-3 control-label">url匹配表达式</label>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="col-sm-9">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type="text" class="form-control" name="zoneUrlRegex" id="zoneUrlRegex_'+curIndex+'" placeholder="输入url" value="'+Feng.getProp(ruleData,"urlRegex","")+'">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="form-group">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label for="zoneExtractContext_'+curIndex+'" class="col-sm-3 control-label">抽取上下文表达式</label>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="col-sm-9">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type="text" class="form-control" name="zoneExtractContext" id="zoneExtractContext_'+curIndex+'" placeholder="" value="'+Feng.getProp(ruleData,"selectExpression","")+'">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="panel panel-default">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="panel-heading">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h3 class="panel-title">字段抽取规则</h3>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<i class="glyphicon glyphicon-plus heading-font-btn" role="addField" onclick="TemplateMgr.appendFieldRule('+curIndex+')"></i>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="panel-body" id="fieldRuleRegion_'+curIndex+'" role="fieldRuleRegion">\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
        '\t\t\t\t\t\t\t\t\t\t\t\t</div>'

    $('#regionTip').hide();
    $('#accordion').append(html);

    var fieldRules = Feng.getProp(ruleData,'fieldRules');
    if(fieldRules && fieldRules.length > 0) {
        $.each(fieldRules,function (i, fieldRule) {
            TemplateMgr.appendFieldRule(curIndex,fieldRule);
        });
    }else{
        TemplateMgr.appendFieldRule(curIndex);
    }
};

/**
 * 添加字段规则
 * @param regionIndex
 */
TemplateMgr.appendFieldRule = function (regionIndex, fieldRuleData) {
    var fieldRegions = $('#fieldRuleRegion_' + regionIndex + ' > .fieldRuleContainer');
    var curIndex = fieldRegions.length;

    var fieldRuleHtml = '<div class="fieldRuleContainer" id="'+TemplateMgr.buildFieldRegionId(regionIndex,curIndex)+'">\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="input-group">\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="input-group-btn">\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<button data-toggle="dropdown" class="btn btn-white dropdown-toggle"\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\ttype="button">字段名\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</button>\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type="text" class="form-control" name="fieldName" placeholder="字段名" value="'+Feng.getProp(fieldRuleData,'fieldName','')+'"/>\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="input-group">\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="input-group-btn">\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<button data-toggle="dropdown" class="btn btn-white dropdown-toggle"\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\ttype="button">规则表达式\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</button>\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type="text" class="form-control" name="rule" value=""/>\n' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
    '<i class="glyphicon glyphicon-minus field-delete-btn" role="delete" onclick="TemplateMgr.deleteFieldRule('+regionIndex+','+curIndex+')"></i>' +
    '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="hr-line-dashed"></div>\n';

    var $html = $(fieldRuleHtml);
    $html.find('input[name="rule"]').val(Feng.getProp(fieldRuleData,'rule',"xpath(li[@class='floorname']/a/text()).get()"));
    $('#fieldRuleRegion_' + regionIndex).append($html);
};

TemplateMgr.deleteRegionRule = function (regionIndex) {
    Feng.confirm("是否删除此区域规则?",function () {
        var regionId = TemplateMgr.buildRegionId(regionIndex);
        $('#' + regionId).remove();
    });
}

TemplateMgr.deleteFieldRule = function (regionIndex,fieldIndex) {
    Feng.confirm("是否删除此字段抽取规则?",function () {
        var fieldId = TemplateMgr.buildFieldRegionId(regionIndex,fieldIndex);
        $('#' + fieldId).remove();
    });
}


TemplateMgr.buildRegionId = function (regionIndex) {
    return "regionContainer_" + regionIndex;
}

TemplateMgr.buildFieldRegionId = function(regionIndex,fieldIndex){
    return 'fieldRuleContainer_' + regionIndex + '_' + fieldIndex;
}

$(function () {
    TemplateMgr.getTemplates(function (templateDataList){
        /*if(templateDataList && templateDataList.length > 0) {
            TemplateMgr.selectTemplate(templateDataList[0].id);
        }*/
    });

    $('#template_list a').click(function(e){
        TemplateMgr.selectTemplate($(e.target).data('templateid'));
    });
});
