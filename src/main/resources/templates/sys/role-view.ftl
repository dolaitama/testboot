<!DOCTYPE html>
<html>
<head>
  <title>权限管理</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/bootstrap-3.0.3/css/bootstrap.min.css" />
  <link rel="stylesheet" href="${request.contextPath}/static/js/plugins/jstree-3.3/dist/themes/default/style.min.css" />
  <link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/alert.css" />
  <script type="text/javascript" src="${request.contextPath}/static/js/jquery-3.4.1.min.js" charset="utf-8"></script>
  <script type="text/javascript" src="${request.contextPath}/static/js/common/alert.js"></script>
  <script type="text/javascript" src="${request.contextPath}/static/js/plugins/bootstrap-3.0.3/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${request.contextPath}/static/js/plugins/jstree-3.3/dist/jstree.js"></script>
  <style type="text/css">
    body {
      margin: 0;
      font-family: Verdana,"Helvetica Neue", Helvetica, Arial, sans-serif;
      color: #333333;
      background-color: #ffffff;
      font-weight:normal;
    }
    label{
        font-size: 14px;
        font-weight:normal;
    }
    .col-sm-8 {
      width: 50%;
    }
  </style>
  <script type="text/javascript" class="source">
    $(function () {
      $("#functions").jstree({ 
          "plugins" : [ "themes", "html_data", "checkbox", "sort", "ui" ],
          "core" : {
              "animation" : 100,
              "initially_open" : [${firstLevelFun!""} ]
          },
          "checkbox": { "three_state" : false }
      });
      $("#functions").jstree('open_all');

      $("#functions").on('select_node.jstree', function(e, o, c){
    	  var node = o.node;
    	  var pnode = $("#functions").jstree(true).check_node(node.parent);
      });
      
    });

    var api = frameElement.api, W = api.opener;    
    function do_submit() { 
      var dialog_object=W.get_dialog_instance();
      dialog_object.button({id:'ok',disabled: true});         
         
      if($('#name').val() == ""){
    	$alert.warning("请输入权限名称！");
        $('#name').focus();
        dialog_object.button({id:'ok',disabled: false});
        return;
      }          
	  var formData=$('#roleform').serialize();
	  console.log("the formData is :%o",formData);
	  var functionid = get_checked_right();
      console.log("the right is :%o",functionid);
      formData += "&functionid="+functionid;
	  console.log("the after formData is :%o",formData)
	  $.ajax({
	       type:'post',
	       url:'${request.contextPath}/role/save',
	       data:formData,
	       cache:false,
	       dataType:'json',
	       async : false, //默认为true 异步
	       success:function(data){
	         if(data.success){
	           W.alertTip('操作成功');
	           api.get("infosource_dialog",1).close();
	           W.query1();
	         }else{
	           $alert.danger('操作失败');
	           dialog_object.button({id:'ok',disabled:false});
	         }
	       }
      });
    }

    function get_checked_right(){
      var checked_ids = $("#functions").jstree("get_checked");
      var t = checked_ids.toString();
      return checked_ids.toString();
    }
        
    function showMacDiv(){
      $("#macDiv").css("display","block");
    }
    	
    function dismissMacDiv(){
      $("#macDiv").css("display","none");
    }
    	
  </script>
</head>
    <body>
<div class="panel panel-default">  
        <table class="table">
            <tr>
                <td width="50%">
                    <form  class="form-horizontal"  role="form" id="roleform" name="roleform">
                    <input type="hidden" name="id" id="id" value="${role.id!""}"/>
                    <fieldset>
                        <legend>1.基本信息</legend>
                            <div class="form-group">
                                <label for="name" class="col-sm-4 control-label" style="font-weight:normal;">角色名称<span style="color:red">*</span></label>
                                <div class="col-sm-8">
                                <input type="input" class="form-control input-sm" name="name" id="name" placeholder="请输入角色名称" value="${role.name}">
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </td>
                <td width="50%">
                    <form role="form"  id="funform" name="funform">
                        <fieldset>
                            <legend>2.请选择权限</legend>
                              <div class="form-group">
                                <div id="functions">
                                <ul>
									${funcTreeStr!""}
                                </ul>
                            </div>                           
                        </div>
                    </fieldset>
                </form>
            </td>
        <tr>
       
    </table>
</div>
    </body>
</html>