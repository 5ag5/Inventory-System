const{createApp}= Vue
const app= createApp({
    data(){
        return{
            user:'',
            oldPassword:'',
            newPassword:''
        }
    },
    created(){ 
        this.getData()
    },
    methods:{
        getData(){
            try{
                axios.get('api/user/current')
                .then(element=>{
                    this.user=element.data;
                    console.log(this.user)
                })
            }catch{
                console.log(err)
            }
            
        },
        changePassword(){
            axios.patch('/api/users/password',`password=${this.newPassword}&oldPassword=${this.oldPassword}`)
            .then(response=>{
                Swal.fire({
                    title:'Maked!',
                    text:'You have changed your password',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/userAccount.html'})
                    }
                }) 
            })
            .catch(function(error){
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                   
                  })
            })

        }
    }
})
app.mount('#app')