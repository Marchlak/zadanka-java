from zeep import Client
from requests import Session
from zeep.transports import Transport
import urllib3
urllib3.disable_warnings()
session = Session()
session.verify = False
transport = Transport(session=session)
client = Client('https://localhost:8443/rsi-1.0-SNAPSHOT/HelloWorld?wsdl', transport=transport)
print(client.service.getHelloWorldAsString('Taki Gość'))

