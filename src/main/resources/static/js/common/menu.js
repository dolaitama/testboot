
var menu = (function() {

    var _instance_dialog_api;
    return {    	
        get_dialog_instance : function(){
            return _instance_dialog_api;
        },
        create_dialog_identy : function(id,title,url,width,height,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
			_instance_dialog_api=$.dialog({
    			id: id,    
    			content: 'url:'+ctx+url,
    			title:title,
    			width:width,
    			height:height,
    			lock:lock,
    			max: false,
    		    min: false,
				ok: function(){
					this.content.do_submit();
					return false;
    			},
    			okVal:'保存',
    			cancelVal: '关闭',
    			cancel: true 
			});         			
        },
        create_read_only_dialog_identy : function(id,title,url,width,height,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
			_instance_dialog_api=$.dialog({
    			id: id,    
    			content: 'url:'+ctx+url,
    			title:title,
    			width:width,
    			height:height,
    			lock:lock,
    			max: false,
    		    min: false,
				ok: function(){
					this.content.do_submit();
					return false;
    			},
    			cancelVal: '关闭',
    			cancel: true 
			});         			
        },
        create_model_dialog_identy : function(id,title,url,width,height,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
            _instance_dialog_api=$.dialog({
                id: id,    
    			max: false,
    		    min: false,                
                content: 'url:'+ctx+url,
                title:title,
                width:width,
                height:height
            });                     
        },                
        create_child_dialog_identy : function(api,W,id,title,url,width,height,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
            _instance_dialog_api=W.$.dialog({
                id: id,   
    			max: false,
    		    min: false,
                content: 'url:'+ctx+url,
                title:title,
                parent:api,
                width:width,
                height:height,
                lock:lock,
                ok: function(){
                    this.content.do_submit();
                    return false;
                },
                cancelVal: '关闭',
                cancel: true 
            });
        },
        create_max_dialog_identy : function(id,title,url,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
        	_instance_dialog_api=$.dialog({
        		id: id,    
        		content: 'url:'+ctx+url,
                max: true,
                min: true,                
        		title:title,
        		lock:lock,
        		ok: function(){
        			this.content.do_submit();
        			return false;
        		},
        		okVal: '保存',
        		cancelVal: '关闭',
        		cancel: true         			
        	}).max();        			
        },
        create_read_only_max_dialog_identy : function(id,title,url,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
        	_instance_dialog_api=$.dialog({
        		id: id,    
        		content: 'url:'+ctx+url,
                max: false,
                min: false,                
        		title:title,
        		lock:lock,
        		ok: function(){
        			this.content.do_submit();
        			return false;
        		},
        		cancelVal: '关闭',
        		cancel: true         			
        	}).max();        			
        },
        create_child_max_dialog_identy : function(api,W,id,title,url,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
        	_instance_dialog_api=W.$.dialog({
        		id: id,    
        		content: 'url:'+ctx+url,
                max: false,
                min: false,                
        		title:title,
        		parent:api,
        		lock:lock,
        		ok: function(){
        			this.content.do_submit();
        			return false;
        		},
        		cancelVal: '关闭',
        		cancel: true         			
        	}).max();        			
        },
        create_model_max_dialog_identy : function(id,title,url,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
            _instance_dialog_api=$.dialog({
                id: id,    
                content: 'url:'+ctx+url,
                max: false,
                min: false,                
                title:title,
                lock:lock
            }).max();;                  
        },//2014-04-22                    
        create_max_dialog_withCancel_identy : function(id,title,url,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
        	_instance_dialog_api=$.dialog({
        		id: id,    
        		content: 'url:'+ctx+url,
                max: false,
                min: false,                
        		title:title,
        		lock:lock,
        		ok: function(){
        			this.content.do_confirm();
        			return false;
        		},
        		cancelVal: '取消',
        		cancel: function(){
        			this.content.do_cancel();
        			return false;
        		}        			
        	}).max();;        			
        },
        create_detail_dialog : function(id,title,url,width,height,lock){
        	if(typeof(lock)=="undefined"){
        		lock = true;
        	}
			_instance_dialog_api=$.dialog({
    			id: id,    
    			content: 'url:'+ctx+url,
    			title:title,
    			width:width,
    			height:height,
    			lock:lock,
    			max: false,
    		    min: false,
    			cancelVal: '关闭',
    			cancel: true 
			});         			
        }
    };
})();
