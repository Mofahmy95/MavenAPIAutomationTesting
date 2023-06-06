package ClientReader;

import io.github.cdimascio.dotenv.Dotenv;

public class BaseReader {
            Dotenv dotenv = Dotenv.configure().filename(".env").load();
            private final String url = dotenv.get("BASE_URL");
            public String getUrl() {return url;}
        }