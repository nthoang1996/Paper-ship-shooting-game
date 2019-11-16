package hoangnt.student.paper_battleship;

import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
//    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    ArrayList<Language> listLanguage;
    String imageLanguage;
    ArrayList<String> numberlist = new ArrayList<String >();
    SQLiteDatabase db;
    ImageButton imgCurrentLanguage;
    File storagePath;
    String myDbPath;
    User user;
    ArrayList<Item> listItem;
    ArrayList<BattleHistory> listBattleHistory;
    TextView txtViewUserLevel;
    TextView txtViewUserName;
    TextView txtViewUserExp;
    ProgressBar progressBarLevel;
    Integer[] listImageId;
    Integer[] listDescriptionId;
    Setting setting;
    Button btnSaveProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storagePath = getApplication().getFilesDir();
        myDbPath = storagePath + "/" + "myGameDatabase";
        setting = new Helper(getApplication()).getSetting();
//        setLocale(currentLanguageKey);
        setContentView(R.layout.activity_setting);
        user = new User(setting.getUserName(), 0);
        listItem = new ArrayList<Item>();
        listItem = new Helper(getApplication()).getListItem();
        listBattleHistory = new ArrayList<BattleHistory>();
        listBattleHistory = new Helper((getApplication())).getListBattleHistory();
        listImageId = new Integer[30];
        listDescriptionId = new Integer[30];
        for(int i = 0; i<30; i ++){
            if(i< listItem.size()){
                listImageId[i] = getResources().getIdentifier(listItem.get(i).getImageName(),"drawable", getPackageName());
                listDescriptionId[i] = getResources().getIdentifier(listItem.get(i).getImageName(), "string", getPackageName());
            }
            else {
                listImageId[i] = getResources().getIdentifier("unreached_skill", "drawable", getPackageName());
                listDescriptionId[i] = getResources().getIdentifier("unreached_skill", "string", getPackageName());
            }
        }
        user.setLevel(listItem.size());
        txtViewUserLevel = findViewById(R.id.text_level_user);
        txtViewUserLevel.setText(Integer.toString(user.getLevel()));
        txtViewUserName = findViewById(R.id.text_user_name);
        txtViewUserName.setText(user.getName());
        txtViewUserExp = findViewById(R.id.text_user_exp);
        txtViewUserExp.setText("" + listItem.get(listItem.size()-1).getCurExp() + "/" + listItem.get(listItem.size()-1).getDesExp());
        progressBarLevel = findViewById(R.id.progressBarLevel);
        progressBarLevel.setMax(listItem.get(listItem.size()-1).getDesExp());
        progressBarLevel.setProgress(listItem.get(listItem.size()-1).getCurExp());
        listLanguage = new ArrayList<Language>();
        Configuration conf = getResources().getConfiguration();
        conf.locale = new Locale(setting.getLanguage());
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Resources resources = new Resources(getAssets(), metrics, conf);
        viewPager = findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), resources, listItem, listImageId, listDescriptionId, listBattleHistory , this, getPackageName());
        viewPager.setAdapter(adapter);

        tabLayout= findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // path to internal memory file system (data/data/cis470.matos.databases)

//        parseXML();
        getCurrentLanguage();
        Integer resource = getResources().getIdentifier(imageLanguage,"drawable", getPackageName());

        imgCurrentLanguage = (ImageButton) findViewById(R.id.buttonChangeLanguage);
        imgCurrentLanguage.setImageResource(resource);
        imgCurrentLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangeLanguage();
            }
        });

        btnSaveProfile = (Button) findViewById(R.id.btnSaveProfile);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getUserInfoNListItem(){

    }

//    private void parseXML() {
//        XmlPullParserFactory parserFactory;
//        try{
//            parserFactory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = parserFactory.newPullParser();
//            InputStream is = getAssets().open("setting1.xml");
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
//            parser.setInput(is, null);
//            processParsing(parser);
//        }catch (XmlPullParserException ex){
//            Log.d("My-Ex", ex.getMessage());
//        }catch (IOException ex){
//            Log.d("My-Ex", ex.getMessage());
//        }
//    }

//    private void processParsing(XmlPullParser parser) {
//        try {
//            int eventType= parser.getEventType();
//
//            while (eventType != XmlPullParser.END_DOCUMENT){
//                if (eventType == XmlPullParser.START_TAG){
//                    if(parser.getName().equalsIgnoreCase("setting")){
//                        currentLanguageKey = parser.nextText();
//                    }
//                }
//                eventType = parser.next();
//            }
//        } catch (XmlPullParserException ex) {
//            Log.d("My-Ex", ex.getMessage());
//        } catch (IOException ex) {
//            Log.d("My-Ex", ex.getMessage());
//        }
//    }

//    public void getLanguageList(JSONObject languageList){
//        Iterator<String> iter = languageList.keys();
//        while (iter.hasNext()) {
//            String key = iter.next();
//            String name;
//            String text;
//            try {
//                JSONObject value = (JSONObject) languageList.get(key);
//                name = value.getString("icon");
//                text = value.getString("value");
//                listLanguage.add(new Language(key,text,name));
//            } catch (JSONException e) {
//                // Something went wrong!
//            }
//        }
//    }

    public void getCurrentLanguage(){
        String json;

        try {
            InputStream is = getAssets().open("mydata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            JSONObject obj = jsonArray.getJSONObject(0);
            JSONArray listLanguageObject = (JSONArray) obj.get("languageList");
            for (int i=0; i<listLanguageObject.length(); i++ ){
                JSONObject item = listLanguageObject.getJSONObject(i);
                if(item.getString("key").equals(setting.getLanguage())){
                    imageLanguage = item.getString("icon");
                }
                listLanguage.add(new Language(item.getString("key"),item.getString("value"),item.getString("icon")));
            }
        } catch (IOException ex) {
            Log.d("My-Ex", ex.getMessage());
        } catch (JSONException ex) {
            Log.d("My-Ex", ex.getMessage());
        }
    }

    private void dialogChangeLanguage(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_language);

        final RadioGroup groupRadio = (RadioGroup) dialog.findViewById(R.id.groupRadioLanguage);
        Button btnSave = (Button) dialog.findViewById(R.id.btnSaveLanguage);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelChangeLanguage);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = groupRadio.getCheckedRadioButtonId();
                String key_selected = listLanguage.get(selectedId).getKey();
                try{
                    db = SQLiteDatabase.openDatabase(myDbPath, null,
                            SQLiteDatabase.CREATE_IF_NECESSARY);
                    db.beginTransaction();
//                    setLocale(key_selected);
                    db.execSQL("UPDATE tblSetting SET language='" + key_selected +"';");
                    db.setTransactionSuccessful();
                }catch (Exception ex){
                    Log.d("Db-Ex", ex.getMessage());
                }finally {
                    db.endTransaction();
                    db.close();
                }
                System.exit(0);

//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder = null;
//                Document doc;
//                try {
//                    builder = factory.newDocumentBuilder();
//                    doc = builder.parse(getAssets().open("setting1.xml"));
//                    NodeList nodeslist = doc.getElementsByTagName("setting");
//                    for(int i= 0; i< nodeslist.getLength(); i ++){
//                        Node node = nodeslist.item(i);
//                        Text text = (Text) ((Element) node).getChildNodes().item(0);
//                        text.setNodeValue("en");
//                    }
//                    TransformerFactory writeFactory = TransformerFactory.newInstance();
//                    Transformer transformer = writeFactory.newTransformer();
//
//                    DOMSource source = new DOMSource(doc);
//                    StreamResult result = new StreamResult("setting1.xml");  // To save it in the Internal Storage
//                    transformer.transform(source, result);
//
//                } catch (ParserConfigurationException ex) {
//                    Log.d("My-Ex", ex.getMessage());
//                } catch (FileNotFoundException ex) {
//                    Log.d("My-Ex", ex.getMessage());
//                } catch (IOException ex) {
//                    Log.d("My-Ex", ex.getMessage());
//                } catch (SAXException ex) {
//                    Log.d("My-Ex", ex.getMessage());
//                } catch (TransformerConfigurationException e) {
//                    e.printStackTrace();
//                } catch (TransformerException ex) {
//                    Log.d("My-Ex", ex.getMessage());
//                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        for(int i =0; i<listLanguage.size(); i++){
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(i);
            rdbtn.setText(listLanguage.get(i).getText());
            if(listLanguage.get(i).getName() == imageLanguage){
                rdbtn.setChecked(true);
            }
            groupRadio.addView(rdbtn);
        }
        dialog.show();
    }

    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
