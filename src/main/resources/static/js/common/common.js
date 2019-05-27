function getRequestParamsStr(){
      var result="";
      var inputs=document.getElementsByName("rptForm")[0].getElementsByTagName("input");
      for(var j=0;j<inputs.length;j++){
      	var name=inputs[j].getAttribute("name");
      	if(name!=null&&inputs[j].value!=null){
      		result+="&";
      		result+=name;
      		result+="=";
      		result+=inputs[j].value;
      	}
      } 
  
      var selects =document.getElementsByName("rptForm")[0].getElementsByTagName("select");
      for(var j=0;j<selects.length;j++){
      	var name=selects[j].getAttribute("name");
      	if(name!=null&&selects[j].value!=null){
      		result+="&";
      		result+=name;
      		result+="=";
      		result+=selects[j].value;      	 	
      	 
      	}
      }   
      return result; 
}