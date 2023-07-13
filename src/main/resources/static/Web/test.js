const {createApp} = Vue

const app = createApp({
    data(){
        return{
            users: undefined
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
        axios.get('http://localhost:8080/api/graphs/auditProductsCount/Price Purchase')
            .then(elemento =>{
                console.log(elemento)
            })
        }
    },
})

app.mount('#app')