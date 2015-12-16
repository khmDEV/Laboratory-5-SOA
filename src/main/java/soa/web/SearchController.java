package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }

		public static int getInteger(String s,int def) {
		    try {
		        return Integer.parseInt(s);
		    } catch(Exception e) {
		        return def;
		    }
		}

    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q,
																	@RequestParam(value="u",required = false) String u,
																	@RequestParam(value="m",required = false) String m) {
				HashMap<String,Object> headers = new HashMap<String,Object>();
				System.out.println(q);
				System.out.println(q+(u!=null&&u!=""?" from:"+u:""));
				System.out.println(m);
				headers.put("CamelTwitterKeywords", q+(u!=null&&u!=""?" from:"+u:""));
				headers.put("CamelTwitterCount", (m!=null&&m!=""?getInteger(m,10):10));
        return producerTemplate.requestBodyAndHeaders("direct:search", "",headers);
    }
}
