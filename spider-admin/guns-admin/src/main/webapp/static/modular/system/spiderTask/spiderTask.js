/**
 * 任务管理模块
 */
var SpiderTaskMgr = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null
};

/**
 * 初始化表格的列
 */
SpiderTaskMgr.initColumn = function () {
    var columns = [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '序号', field: 'SerialNumber', align: 'center', valign: 'middle', formatter: function (value, row, index) {return index+1;}},
        {title: '任务名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '模板名称', field: 'seedUrl.url', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: 'operate', align: 'center', valign: 'middle', formatter : function(value, row, index){
            var taskId = row.taskIdentifier.id;
            return [
                '<a class="delete" href="javascript:SpiderTaskMgr.viewData(\''+taskId+'\')">数据查看</a>&nbsp;&nbsp;'
            ].join('');
        }}];
    return columns;
};

SpiderTaskMgr.viewData = function (templateId) {
    var ajax = new $ax(Feng.ctxPath + '/spiderTask/viewData/' + templateId,function (data) {
        layer.open({
            title: '爬取内容'
            ,closeBtn: true
            ,area: '700px;'
            ,shade: 0.8
            ,content: SpiderTaskMgr.buildContent(data)
        });
    });
    ajax.type = 'get';
    ajax.start();
};

SpiderTaskMgr.buildContent = function (data) {
    if(data.success) {
        var ary = [];
        var crawlData = data.data;
        $.each(crawlData,function(i,item){
            var regionData = item.regionData;
            ary.push('<div>' +
                        '<h4>爬取url:<span>'+item.pageUrl+'</span></h4>' +
                        '<p>爬取内容如下: </p>' +
                        '<div class="panel panel-default">' +
                            '<div class="panel-body">' +
                                '<p>区域：'+regionData.regionName+'</p>');
            for(var title in regionData.dataMap){
                            ary.push('<p>'+title+'：'+ regionData.dataMap[title].sourceTexts.join('')+'</p>')
            }
            ary.push(       '</div>' +
                        '</div>' +
                    '</div>');
           /* var pageUrl = item.pageUrl;
            ary.push('爬取地址：')
            var regionData = item.regionData;*/

        });

        return ary.join('');
    }else{
        return data.errorMsg;
    }
}

$(function () {
    showTab(1);

    $('#myTab a').click(function (e) {
        e.preventDefault();//阻止a链接的跳转行为
        showTab($(e.target).attr("data-status"));
        /*$(this).tab('show');//显示当前选中的链接及关联的content
        table = new BSTable("managerTable", "/task/list?taskState="+$(e.target).attr("id"), defaultColunms);
        Task.table.refresh(table.init());*/
    })

    function showTab(taskState,loadSuccessCallback){
        $('#managerTable').bootstrapTable('destroy');
        $('#myTab a[id="'+taskState+'"]').tab('show');
        var defaultColunms = SpiderTaskMgr.initColumn();
        var table = new BSTable("managerTable", "/spiderTask/list/"+taskState, defaultColunms);
        table.setPaginationType("client");
        SpiderTaskMgr.table = table.init();
        /*if(SpiderTaskMgr.table){
            SpiderTaskMgr.table.refresh();
        }else{
            SpiderTaskMgr.table = table.init();
        }*/

        $('#managerTable').off('load-success.bs.table').on('load-success.bs.table', function (data) {
            if(loadSuccessCallback){
                loadSuccessCallback();
            }
        });
    }
});
