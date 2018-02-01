import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.ResourceQuery;


public class RetrieveStats {
    static String host = "http://yy.yy.yy.yy:9000";
    static String resourceKey = "com.pbmbank:pbm";
    static String[] MEASURES_TO_GET = new String[]{"new_code_smells", "new_bugs", 
                                                   "new_vulnerabilities", };
 
    public static void main(String[] args) {                
        try {       
            //setup
            DecimalFormat df = new DecimalFormat("#.##");            
            Date date = new Date();
 
            //header
            System.out.println("<html>");
            System.out.println("<head>");
            System.out.println("<body>");
            //System.out.println("<ol>************************************</ol>");
            System.out.println("<br>Code trend for the past 1 days as of "+date+"</br>");
            //System.out.println("<ol>************************************</ol>");
 
            //do the work of getting the data
            Sonar sonar = new Sonar(new HttpClient4Connector(new Host(host)));
            ResourceQuery query = ResourceQuery.createForMetrics(resourceKey, MEASURES_TO_GET);
            query.setIncludeTrends(true);
            org.sonar.wsclient.services.Resource resource = sonar.find(query);
            //loop through them
            //getVariation2 for "7 days"
            List<Measure> allMeasures = resource.getMeasures();
            for (Measure measure : allMeasures) {
                System.out.println("<br>"+measure.getMetricKey()+": "+df.format(measure.getVariation2())+"</br>");
            }     
            System.out.println("</html>");
            System.out.println("</head>");
            System.out.println("</body>");
        } catch (Exception e) {
            e.printStackTrace();
       }
    }
}

