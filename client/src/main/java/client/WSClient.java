package client;

import java.util.List;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import com.feisystems.bham.service.recommendation.AbstractVariableContainer;
import com.feisystems.bham.service.recommendation.Age;
import com.feisystems.bham.service.recommendation.GpraVariableContainer;
import com.feisystems.bham.service.recommendation.PregnantInd1;
import com.feisystems.bham.service.recommendation.PsycholEmotMedication;
import com.feisystems.bham.service.recommendation.RecommendationService;
import com.feisystems.bham.service.recommendation.RecommendationServiceImplService;
import com.feisystems.bham.service.recommendation.RecommendationsDto;

public class WSClient {

    // Stand-alone Java client
    public static void main (String[] args) {
       	
       RecommendationServiceImplService service = new RecommendationServiceImplService();
 	   RecommendationService port = service.getRecommendationServiceImplPort();
 	   	   
       // next three lines optional, they output the SOAP request/response XML
       Client client = ClientProxy.getClient(port);
       client.getInInterceptors().add(new LoggingInInterceptor());
       client.getOutInterceptors().add(new LoggingOutInterceptor()); 

       AbstractVariableContainer variableContainer = new  AbstractVariableContainer();
       GpraVariableContainer gpraVariableContainer = new GpraVariableContainer();
       
       // Build Pregnant Indicator
       PregnantInd1 pregnantInd1 = new PregnantInd1();
       pregnantInd1.setYesNo(1);
       
       Age age = new Age();
       age.setAge(23);

       // Build PsycholEmotMedication
       PsycholEmotMedication psycholEmotMedication = new PsycholEmotMedication();
       psycholEmotMedication.setDays(23);
       psycholEmotMedication.setRefusedDknowMissing(-7);

       gpraVariableContainer.setPregnantInd1(pregnantInd1);
       gpraVariableContainer.setEmotMedication(psycholEmotMedication);
       gpraVariableContainer.setAge(age);

       variableContainer.setGPRA(gpraVariableContainer);
       
       List<RecommendationsDto> dtos =  port.getAllRecommendations(variableContainer);
       RecommendationsDto dto = dtos.get(0);
        
 	   System.out.println(dto.getCode());
 	   System.out.println(dto.getCodeSystem());
 	   System.out.println(dto.getCodeSystemName());
 	   System.out.println(dto.getDisplayName());

    } 

}
