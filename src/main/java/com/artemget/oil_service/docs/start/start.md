# Start guide

1. Install [docker](https://www.docker.com/get-started) on your machine.

> login required!

2. Clone [project](https://github.com/ArtemGet/oil_service).      
           
3. Open terminal/console and go to the project's root.      
         
4. Run docker command:
> docker compose up -d --build      
            
5. This command will deploy postgres container on localhost:5433 and app container on localhost:8080     
   
6. Download curl/[postman](https://www.postman.com/downloads/) etc to send requests.     
> Go watch [API docs](https://github.com/ArtemGet/oil_service/blob/master/src/main/java/com/artemget/oil_service/docs/api/api_main.md) to get familiar with app's api.      
>> default account with admin roots: nickname: admin, password: 1234     
7. To stop app run command:
> docker stop oil_service
> docker stop pgdb