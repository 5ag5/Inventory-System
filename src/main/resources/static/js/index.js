const{createApp}= Vue
console.log("hola2")
const app=createApp({
    data(){
        return {
            login: '',
            password:'',
            unsuccessfulAttempts:0
        }
    },
   
    methods:{
      singIn(){
        if( this.unsuccessfulAttempts<3){
          axios.post('/api/login', `login=${this.login}&password=${this.password}`)
            .then(response=>{
              Swal.fire({
                icon: 'success',
                text: 'you have successfully logged in to your account',
                showConfirmButton: false,
                iconColor:"#324545",
                timer: 2000,
              }).then(() => window.location.href = "/userAccount.html")
            })
            .catch(error=>{
              axios.patch('/api/users/validateAttempts', `login=${this.login}`)
              .catch(error=>{
                Swal.fire({
                  icon: 'error',
                  text: error.response.data,
                  confirmButtonColor: "#7c601893",
                })
                this.unsuccessfulAttempts=this.unsuccessfulAttempts+1
                console.log(error);
              })
            
        })
            console.log(this.login+" "+this.password)
          }else{
          
            Swal.fire({
              icon: 'error',
              text: 'your account has been blocked',
              confirmButtonColor: "#7c601893",
            })
          }
        }
        
        
    }
})
app.mount('#app')

const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");
});

sign_in_btn.addEventListener("click", () => {
  container.classList.remove("sign-up-mode");
});
