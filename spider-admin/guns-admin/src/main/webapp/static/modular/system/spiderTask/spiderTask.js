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
        /*{title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},*/
        {title: '序号', field: 'SerialNumber', align: 'center', valign: 'middle', formatter: function (value, row, index) {return index+1;}},
        {title: '任务名称', field: 'id', align: 'center', valign: 'middle', sortable: true},
        {title: '模板名称', field: 'seedUrl.url', align: 'center', valign: 'middle', sortable: true},
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

$(function () {
    showTab(1);

    $('#myTab a').click(function (e) {
        $('#managerTable').bootstrapTable('destroy');
        e.preventDefault();//阻止a链接的跳转行为
        showTab($(e.target).attr("data-status"));
        /*$(this).tab('show');//显示当前选中的链接及关联的content
        table = new BSTable("managerTable", "/task/list?taskState="+$(e.target).attr("id"), defaultColunms);
        Task.table.refresh(table.init());*/
    })

    function showTab(taskState,loadSuccessCallback){
        $('#myTab a[id="'+taskState+'"]').tab('show');
        var defaultColunms = SpiderTaskMgr.initColumn();
        var table = new BSTable("managerTable", "/spiderTask/list/"+taskState, defaultColunms);
        table.setPaginationType("client");
        if(SpiderTaskMgr.table){
            SpiderTaskMgr.table.refresh();
        }else{
            SpiderTaskMgr.table = table.init();
        }

        $('#managerTable').off('load-success.bs.table').on('load-success.bs.table', function (data) {
            if(loadSuccessCallback){
                loadSuccessCallback();
            }
        });
    }
});
