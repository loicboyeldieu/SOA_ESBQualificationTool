package esbqualificationtoolconsumerlauncher;

public class Main {

    public static void main(String[] args) {

        try { // Call Web Service Operation
            esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1 service = new esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1();
            esbqualificationtoolcompositeapp.WebServiceProducer port = service.getCasaPort1();
            // TODO initialize WS operation arguments here
            int byteSize = 0;
            int msProcessingTime = 0;
            // TODO process result here
            byte[] result = port.qualificationToolService(byteSize, msProcessingTime);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

    }

}
