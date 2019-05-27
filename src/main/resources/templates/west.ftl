<style type="text/css">
#navsite { 
    font-family: Verdana, Helvetica, Arial, sans-serif; 
    font-size: 0.7em; 
    font-weight: bold; 
    padding: 0; 
    margin-bottom: 1em;  
    color: #333; 
} 
#navsite ul { 
    list-style: none; 
    margin: 0; 
    padding: 0; 
} 
#navsite ul li { 
    margin: 0; 
} 
#navsite ul li a { 
    display: block; 
    padding: 2px 2px 2px 1em; 
    background-color: #fff; 
    color: #121a2a; 
    text-decoration: none; 
    width: 100%; 
} 
#navsite ul li a:hover { 

    background-color: #69f; 
    color: #fff; 
} 
  
</style>
<script type="text/javascript">

    var first_child_menu=null;

	$(function() {
        init_menu();    
        setTimeout(function () { 
            if(first_child_menu!=null){
                eu.addTab('layout_center_tabs',first_child_menu.text,'${request.contextPath}' + first_child_menu.attributes.url,true, first_child_menu.iconCls);
            }  
        }, 600);
	});

    function init_menu(){
    	console.log("=====>",${RequestParameters['menuId']!""})
        $.ajax ({
           type: "POST",
           url: "${request.contextPath}/list/navTreeListByMenu?menuId=${RequestParameters['menuId']!""}" ,
           data: "", 
           async: false,
           success: function(data) {    //提交成功后的回调    
            $.each(data, function(i, n) {
            	if(i>0){
            		return;
            	}
                var menulist = "<div class='easyui-panel' data-options='fit:true,border:false' style='overflow-y:auto;overflow-X: hidden;' id='navsite'><ul>";
                $.each(n.children, function(j, o) {//依赖于center界面选项卡layout_center_tabs对象
                    if(j==0){
                        first_child_menu=o;
                    }
                    menulist += "<li><div><strong><a onClick='javascript:eu.addTab(layout_center_tabs,\""
                        + o.text+"\",\"${request.contextPath}" + o.attributes.url+ "\",true,\""+o.iconCls+"\")' style='font-size:14px;' > " + o.text + "</a></strong></div></li> ";
                });
                menulist += '</ul></div></br>';
             
                $("#page_nav").html(menulist);
                
            });
              
            $('.easyui-accordion div li div strong a').click(function(){
                $('.easyui-accordion li div').removeClass("selected");
                $(this).parent().parent().addClass("selected");
            }).hover(function(){
                $(this).parent().parent().addClass("hover");
            },function(){
                $(this).parent().parent().removeClass("hover");
            });

           }
        });
    }

</script>

<div id="page_nav" data-options="animate:false,fit:true,border:true" >
</div>