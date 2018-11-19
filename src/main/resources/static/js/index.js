axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
var index=new Vue({
    el :"#app",
    data:function(){
        return{
            ruleForm: {
              username:'',
              password:''
        },
        wrongTimes:0,
        showKaptcha:false
        }
    },
 	mounted:function(){
	},
	created(){
		var _self = this;
		document.onkeydown = function(e){
			if(e.keyCode == 13){
				_self.login();
			}
		}
		document.oncontextmenu = function(e){
			_self.msg("点右键干啥",'success')
			return false;
			}
		
	},
    methods:{
    	destroy(){
    		index = null;
    	},
    	msg(message,type,duration){
    		if(!duration){
    			duration = 3000;
    		}
    		if(!type){
    			type = 'success'
    		}
    		this.$message({
                message:message,
                type: type,
                duration:duration
            })
    	},
        login(){
        	if(this.showKaptcha && (this.ruleForm.code == '' ||this.ruleForm.code == undefined)){
        		this.msg('请输入验证码','error',1000)
        		return;
        	}
            if(this.ruleForm.username.trim()==''){
            	this.msg('请输入用户名','error',1000)
                  return;
            }
            if(this.ruleForm.password.trim()==''){
            	this.msg('请输入密码','error',1000)
                  return;
            }
            axios.post("/test",Qs.stringify(this.ruleForm)).then(res=>{
				if(res.data.success){
					this.msg(res.data.success,'success',1000)
					window.location.href='/success';
				}else{
					this.msg(res.data.fail,'error',1000)
					this.wrongTimes = this.wrongTimes+1
					if(this.wrongTimes>3){
            			this.showKaptcha=true;
					}
				   	if(this.showKaptcha){
    	            	document.getElementById("kaptcha").click();
                	}
				}

        }).catch(res=>{
        	this.msg(res.message,'error',1000)
        	console.log(res.message)
        })

        }
    }
})