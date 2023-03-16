# keycloak-event-listener-spi
Event listener SPI for keycloak, for REGISTER event

#Create Private and Public key using openssl
#Generate Private Key:
    openssl genpkey -out private_key_rsa_4096_pkcs8-generated.pem -algorithm RSA -pkeyopt rsa_keygen_bits:4096
#Generate Public Key:
    openssl rsa -pubout -outform pem -in private_key_rsa_4096_pkcs8-generated.pem -out public_key_rsa_4096_pkcs8-exported.pem

Put Public key file (*.pem) to resources/keys folder

#Mapping Target API and Keys
    Open PropertyConstant.java from com.techsophy.awgment.utils
    public static final String ADD_USER_ENDPOINT = "<<Add User API Endpoint>>";

#Build Artifacts (*.jar)
    Run: mvn clean package

#Copy jar file to Keycloak 
    <<Keycloak_Home>>/standalone/deployments/
#Restart Keycloak

#Copy Private(private_key_rsa_4096_pkcs8-generated.pe) key file into the awgment-app-account
#Copy Rsa4096_java_server.txt as Rsa4096.java in awgment-app-account 

Need implementation of following API Endpoint:
POST account-app:8080/internal/v1/users/create?signature=<>
Body:
    {
           realm : "",
           “userDetails” : {
            "userName": "venkataramana",
            "firstName": "venkataramana",
            "lastName": "Guddeti",
            "mobileNumber": "(123) 456-7899",
            "emailId": "venkataramana.g@techsophy.com",
            "department": "testing",
            "groups": [],
            "roles": []
          }
    } 


