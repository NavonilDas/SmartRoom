#include <ESP8266WiFi.h>
#include <PubSubClient.h>

// Update these with values suitable for your network.

const char* ssid = "EspServer";
const char* password = "12345678";
const char* mqtt_server = "iot.eclipse.org";
// 9EB1928E ---> In
// DEDE09CB ---> out
WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[50];
int value = 0;
short check = 0;

void setup_wifi() {
  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  char c = (char)payload[0];
  /// Switch On or Off The Relay
  // 0,1,2,3 -> Relay On
  // 4,5,6,7 -> Relay off
  
  switch(c){
      case '0':
        digitalWrite(D1,LOW);
       break;
      case '1':
        digitalWrite(D2,LOW);
       break;
      case '2':
        digitalWrite(D3,LOW);
       break;
      case '3':
        digitalWrite(D4,LOW);
       break;
      case '4':
        digitalWrite(D1,HIGH);
       break;
      case '5':
        digitalWrite(D2,HIGH);
       break;
      case '6':
        digitalWrite(D3,HIGH);
       break;
      case '7':
        digitalWrite(D4,HIGH);
       break;
      default:
      Serial.println("Not Found Value !");      
    }
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
   
    Serial.print("Attempting MQTT connection...");
    
    // Create a random client ID
    
    String clientId = "ESP8266Client";
    clientId += String(random(0xffff), HEX);

    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      client.publish("DEDE09CB", "Connected");
      client.subscribe("9EB1928E");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {
  Serial.begin(115200);
  ////// Setting Output Pins
  pinMode(D1,OUTPUT);
  pinMode(D2,OUTPUT);
  pinMode(D3,OUTPUT);
  pinMode(D4,OUTPUT);
  //// Setting ALL values to High
  digitalWrite(D1,HIGH);
  digitalWrite(D2,HIGH);
  digitalWrite(D3,HIGH);
  digitalWrite(D4,HIGH);

  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void loop() {

  if (!client.connected()) {
    check = 0;
    reconnect();
  }
  client.loop();
  if(check == 0){
    client.publish("DEDE09CB", "Connected");
    check = 1;
  }
}
