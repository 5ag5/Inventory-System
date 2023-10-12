const {createApp}= Vue
console.log("gucadss")
const app=createApp({
    data(){
        return{
            parameters:[],
            parameter:'',
            parameterDescription:'',
            parameterStatus:'',
            nameParameter:'',
            valueParameter:'',
            parameterDescriptionM:'',
            parameterStatusM:'',
            nameParameterM:'',
            valueParameterM:''
            
        }
    },
    created(){
        this.getProducts()
    },
    methods:{
        getProducts(){
            axios.get('/api/parameter/allParameters')
            .then(response=>{
                this. parameters=response.data
            })
            .catch(error=>{console.log(error)})
        },
        modifyParameter(){
           
            axios.put('/api/user/changes',`id=${this.parameter.id}&parameterDescription=${this.parameterDescriptionM}&parameterStatus=${this.parameterStatusM}&nameParameter=${this.nameParameterM}&valueParameter=${this.valueParameterM}`)
            .then(
                Swal.fire({
                    title:'Maked!',
                    text:'you changed the information',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/parameters.html'})
                    }
                }) 
            )
            .catch(function(error){
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                   
                  })
            })
        },
    },
})
app.mount('#app')