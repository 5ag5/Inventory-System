const{createApp}= Vue
console.log("hola2")
const app=createApp({
    data(){
        return {
            login: '',
            password:'',
        }
    },
   
    methods:{
      singIn(){
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
          Swal.fire({
            icon: 'error',
            text: 'Wrong password',
            confirmButtonColor: "#7c601893",
          })
          console.log(error);
         
        })
        console.log(this.login+" "+this.password)
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
