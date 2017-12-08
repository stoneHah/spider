/**
 * 模板管理
 */
var TemplateListMgr = {
    id: "templateTable",//表格id
    seItem: null,		//选中的条目
    table: null
};

/**
 * 初始化表格的列
 */
TemplateListMgr.initColumn = function () {
    var columns = [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '序号', field: 'SerialNumber', align: 'center', valign: 'middle', formatter: function (value, row, index) {return index+1;}},
        {title: '模板名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '种子url', field: 'seedUrl.url', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true, formatter: TemplateListMgr.dateFormatter},
        {title: '修改时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: true, formatter: TemplateListMgr.dateFormatter},
        {title: '操作', field: 'operate', align: 'center', valign: 'middle', formatter : function(value, row, index){
            var templateId = row.id;
            return [
                '<a class="delete" href="javascript:TemplateListMgr.delete(\''+templateId+'\')">删除</a>&nbsp;&nbsp;',
                '<a class="delete" href="javascript:TemplateListMgr.edit(\''+templateId+'\')">编辑</a>&nbsp;&nbsp;',
                '<a class="delete" href="javascript:TemplateListMgr.execute(\''+templateId+'\')">执行</a>&nbsp;&nbsp;'
            ].join('');
        }}];
    return columns;
};

TemplateListMgr.openAddLink = function () {
    window.location.href = '/template/add';
};

TemplateListMgr.edit = function (templateId) {
    window.location.href = '/template/update/' + templateId;
};

TemplateListMgr.execute = function (templateId) {
    Feng.confirm("确定要执行此模板?",function () {
        var ajax = new $ax(Feng.ctxPath + '/spiderTask/execute/' + templateId,function (data) {
            if(data.success){
                Feng.success("任务已执行，可进入到任务管理菜单进行查看");
            }else{
                Feng.error(data.msg);
            }
        });
        ajax.start();
    });
};

TemplateListMgr.dateFormatter = function (value, row, index) {
    return TemplateListMgr.formatDateFromMill(value);
}

TemplateListMgr.formatDateFromMill = function(mill){
    if(mill) {
        mill -= 0;
        return (new Date(mill)).Format("yyyy-MM-dd hh:mm:ss")
    }
    return '';
}


$(function () {
    var defaultColunms = TemplateListMgr.initColumn();
    var table = new BSTable(TemplateListMgr.id, "/template/list", defaultColunms);
    table.setPaginationType("client");
    TemplateListMgr.table = table.init();
});
