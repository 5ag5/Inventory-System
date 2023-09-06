const {createApp}= Vue 
const app=createApp({
    data(){
        return{
            parameters:[],
            filteredparameters:[],
            wordSearch:'',
            parameterDescription:'',
            nameParameter :'',
            valueParameter :'',
            parameterDescriptionM:'',
            nameParameterM :'',
            valueParameterM :'',
            parameterStatusM:'',
            idParameter:''
            
            
        }
    },
    created(){
        this.getParameters()
    },
    methods:{
        getParameters(){
            axios.get('/api/parameter/allParameters')
            .then(response=>{
                this.parameters=response.data.filter(parameter=>parameter.parameterStatus)
                this.filteredparameters=response.data.filter(parameter=>parameter.parameterStatus)
            })
            .catch(error=>{console.log(error)})
        },
        deleteParameter(nameParameter){
            axios.patch('/api/parameter/deleteParameter',`nameParameter=${nameParameter}`)
            .then(response=>{
                Swal.fire({
                    title:'Maked!',
                    text:'you deleted the parameter',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/parameters.html'})
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
        },
        newParameter(){
            axios.post('/api/parameter/createParameter',
            {
                "parameterDescription":this.parameterDescription,
                "parameterStatus":true,
                "nameParameter": this.nameParameter,
                "valueParameter": this.valueParameter
            })
            .then(response=>{
                Swal.fire({
                    title:'Maked!',
                    text:'you created a new parameter',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/parameters.html'})
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
        },
        getId(id){
            this.idParameter=id
        }
        ,
        changeInfo(){
            console.log(id +"id del parametro")
            axios.put('/api/user/changes',
            {
                "parameterDescription" :this. parameterDescriptionM,
                "parameterStatus" : this.parameterStatusM,
                "nameParameter" : this.nameParameterM,
                "valueParameter" : this.valueParameterM
               
            }, `id=${this.idParameter}`)
            .then(response=>{
                Swal.fire({
                    title:'Maked!',
                    text:'you changed the information',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/parameters.html'})
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
        },
        search(){
            this.filteredparameters=this.parameters.filter(parameter=>parameter.parameterDescription.toLowerCase().includes(this.wordSearch.toLowerCase()))
            console.log(this.filteredparameters)
        }
    }
})
app.mount('#app')