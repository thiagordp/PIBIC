package control;

import java.util.List;

public class Util {

    public static final int MAX_REC = 5;

    public static String toJson(List<Object> produtos) {

	String json = "{";
	if (produtos != null) {
	    for (int i = 0; i < produtos.size(); i++) {
		Object[] obj = (Object[]) produtos.get(i);

		for (int j = 0; j < 2; j++) {
		    json += "\"" + obj[j] + "\"";

		    if (j == 0) {
			json += ":";
		    }
		}

		if (i < produtos.size() - 1) {
		    json += ",";
		}
	    }

	    json += "}";
	}

	return json;
    }

}
