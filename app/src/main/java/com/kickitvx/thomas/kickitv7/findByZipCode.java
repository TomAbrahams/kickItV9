package com.kickitvx.thomas.kickitv7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Random;
import java.util.Vector;

import static java.lang.Math.abs;

public class findByZipCode extends AppCompatActivity implements AsyncResponse {

    TextView textViewRecEvent;
    private static final Vector<String> args = new Vector<String>();
    private static final Vector<Event> eventArgs = new Vector<Event>();
    private static final Vector<Event> eventArgs2 = new Vector<Event>();

    //***********************************************************
    //ON CREATE EVENT IS HERE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_zip_code);
        //Established text view for the recorded event. Will probably have to redefine it though.
        //Need button on click listener.

        //textViewRecEvent = (TextView)findViewById(R.id.tvRecEvent);
        Bundle bundle = getIntent().getExtras();
        //String theCll = bundle.getString("theCLL");
        int theEventNumber = bundle.getInt("eventNumber");
        String theCll = "";
        TextView tvClickEvent1 =(TextView)findViewById(R.id.etLocation);
        if((tvClickEvent1 != null))
        {
            theCll = tvClickEvent1.getText().toString();
            recommendClick(theCll);
        }
        clickingEvent1(theEventNumber);
        clickingEvent2(theEventNumber);
        clickingEvent3(theEventNumber);
        randomSuggestion();
    }
    public void randomSuggestion()
    {
        final Vector<String> Suggest = new Vector<String>();
        Suggest.add("Hamburgers");
        Suggest.add("Bar");
        Suggest.add("Park");
        Suggest.add("Gym");
        Suggest.add("Movie Theater");
        Suggest.add("Chinese Food");
        Suggest.add("Japanese Food");
        Suggest.add("Steak");
        Suggest.add("Music");
        Suggest.add("Mall");
        Suggest.add("Video Games");
        Suggest.add("Buffet");
        Suggest.add("Electronics");
        Suggest.add("Mexican Food");
        Suggest.add("Bowling");
        Suggest.add("Cake");
        Suggest.add("Beach");
        Suggest.add("University");
        Suggest.add("Disneyland");

        Button mySuggestion = (Button)findViewById(R.id.btnSuggest);
        mySuggestion.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Random random = new Random();
                                                int index = abs(random.nextInt()) % Suggest.size();

                                                String suggestion = Suggest.elementAt(index);
                                                EditText editText = (EditText)findViewById(R.id.etTerm);
                                                editText.setText(suggestion);

                                            }
                                        }


        );




    }
    public void clickingEvent1(final int myEventNumber)
    {
        final int myNumber = myEventNumber;
        final TextView tvClickEvent1 = (TextView)findViewById(R.id.tvRecEvent1Zip);
        tvClickEvent1.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 //Build the box.

                                                 String eventText = tvClickEvent1.getText().toString();
                                                 if ((eventText == "") || (eventText == null))
                                                     eventText = "Nothing here";
                                                 else
                                                 {
                                                     Dialog theDBox = myDialogOne(myNumber,eventText);
                                                     theDBox.show();


                                                 }

                                             }
                                         }

        );

    }
    public void clickingEvent2(final int myEventNumber)
    {
        final int myNumber = myEventNumber;
        final TextView tvClickEvent1 = (TextView)findViewById(R.id.tvRecEvent2Zip);
        tvClickEvent1.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 //Build the box.

                                                 String eventText = tvClickEvent1.getText().toString();
                                                 if ((eventText == "") || (eventText == null))
                                                     eventText = "Nothing here";
                                                 else
                                                 {
                                                     Dialog theDBox = myDialogTwo(myNumber,eventText);
                                                     theDBox.show();


                                                 }

                                             }
                                         }

        );

    }
    public void clickingEvent3(final int myEventNumber)
    {
        final int myNumber = myEventNumber;
        final TextView tvClickEvent1 = (TextView)findViewById(R.id.tvRecEvent3Zip);
        tvClickEvent1.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 //Build the box.

                                                 String eventText = tvClickEvent1.getText().toString();
                                                 if ((eventText == "") || (eventText == null))
                                                     eventText = "Nothing here";
                                                 else
                                                 {
                                                     Dialog theDBox = myDialogThree(myNumber,eventText);
                                                     theDBox.show();


                                                 }

                                             }
                                         }

        );

    }

    public Dialog myDialogOne(int myNumber, String eventDesc) {
        // Use the Builder class for convenient dialog construction
        int eventNumber = 0;
        //final String myCll = theCll;

        final int theEventNumber = myNumber;
        final String eventName = "Event " + myNumber;
        final Context context = this;
        String eventText = eventDesc;
        if ((eventText == "") || (eventText == null))
            eventText = "Nothing here";
        //Load the image.
        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(android.R.drawable.stat_sys_download)
                .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) //filled width
                .build();

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        ImageView imageViewURL = new ImageView(findByZipCode.this);
        imageViewURL.setMinimumHeight(75);
        imageViewURL.setMinimumWidth(75);
        String imageURL = eventArgs2.get(0).getImageURL();
        if(imageURL != "None")
        {
            String url = imageURL;
            ImageLoader.getInstance().displayImage(imageURL, imageViewURL);
        }
        //This is the image ending.

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(eventName);
        builder.setView(imageViewURL);
        builder.setMessage(eventText)
                .setPositiveButton("Save to the Event", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Time to make an event. Get me to the dialog box.
                        Intent myIntent = new Intent(findByZipCode.this, CurrentPlan.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("eventNum",theEventNumber);
                        myIntent.putExtras(bundle);
                        if(eventArgs.size() != 0)
                        {
                            //Where does it go?
                            DefaultPlan theDefaultPlan = new DefaultPlan();
                            Event theEvent = eventArgs.elementAt(0);//My event.
                            if(theEventNumber == 1)
                                theDefaultPlan.setEvent1(eventArgs2.elementAt(0));
                            else if(theEventNumber == 2)
                                theDefaultPlan.setEvent2(eventArgs2.elementAt(0));
                            else if(theEventNumber == 3)
                                theDefaultPlan.setEvent3(eventArgs2.elementAt(0));
                        }
                        startActivity(myIntent);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
//                .setNeutralButton("Learn More",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User wants to know more
//                        Intent myIntent = new Intent(suggestOnGPS.this, Recommendation.class);
//                        Bundle myBundle = new Bundle();
//                        myBundle.putString("imageURL",eventArgs2.get(0).getImageURL());
//                        myBundle.putString("name",eventArgs2.get(0).getEventName());
//                        myBundle.putString("address", eventArgs2.get(0).getEventAddress());
//                        myBundle.putString("theCLL",myCll);
//                        myBundle.putInt("eventNumber", theEventNumber);
//                        myIntent.putExtras(myBundle);
//                        startActivity(myIntent);
//                        //Need to put image url and plan inside the bundle.
//                    }
//                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public Dialog myDialogTwo(int myNumber, String eventDesc) {
        // Use the Builder class for convenient dialog construction
        int eventNumber = 0;
        //final String myCll = theCll;

        final int theEventNumber = myNumber;
        final String eventName = "Event " + myNumber;
        final Context context = this;
        String eventText = eventDesc;
        if ((eventText == "") || (eventText == null))
            eventText = "Nothing here";
        //Load the image.
        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(android.R.drawable.stat_sys_download)
                .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) //filled width
                .build();

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        ImageView imageViewURL = new ImageView(findByZipCode.this);
        imageViewURL.setMinimumHeight(75);
        imageViewURL.setMinimumWidth(75);
        String imageURL = eventArgs2.get(1).getImageURL();
        if(imageURL != "None")
        {
            String url = imageURL;
            ImageLoader.getInstance().displayImage(imageURL, imageViewURL);
        }
        //This is the image ending.

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(eventName);
        builder.setView(imageViewURL);
        builder.setMessage(eventText)
                .setPositiveButton("Save to the Event", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Time to make an event. Get me to the dialog box.
                        Intent myIntent = new Intent(findByZipCode.this, CurrentPlan.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("eventNum",theEventNumber);
                        myIntent.putExtras(bundle);
                        if(eventArgs.size() != 0)
                        {
                            //Where does it go?
                            DefaultPlan theDefaultPlan = new DefaultPlan();
                            Event theEvent = eventArgs2.elementAt(1);//My event.
                            if(theEventNumber == 1)
                                theDefaultPlan.setEvent1(eventArgs2.elementAt(1));
                            else if(theEventNumber == 2)
                                theDefaultPlan.setEvent2(eventArgs2.elementAt(1));
                            else if(theEventNumber == 3)
                                theDefaultPlan.setEvent3(eventArgs2.elementAt(1));
                        }
                        startActivity(myIntent);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
//                .setNeutralButton("Learn More",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User wants to know more
//                        Intent myIntent = new Intent(suggestOnGPS.this, Recommendation.class);
//                        Bundle myBundle = new Bundle();
//                        myBundle.putString("imageURL",eventArgs2.get(0).getImageURL());
//                        myBundle.putString("name",eventArgs2.get(0).getEventName());
//                        myBundle.putString("address", eventArgs2.get(0).getEventAddress());
//                        myBundle.putString("theCLL",myCll);
//                        myBundle.putInt("eventNumber", theEventNumber);
//                        myIntent.putExtras(myBundle);
//                        startActivity(myIntent);
//                        //Need to put image url and plan inside the bundle.
//                    }
//                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public Dialog myDialogThree(int myNumber, String eventDesc) {
        // Use the Builder class for convenient dialog construction
        int eventNumber = 0;
        //final String myCll = theCll;

        final int theEventNumber = myNumber;
        final String eventName = "Event " + myNumber;
        final Context context = this;
        String eventText = eventDesc;
        if ((eventText == "") || (eventText == null))
            eventText = "Nothing here";
        //Load the image.
        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(android.R.drawable.stat_sys_download)
                .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) //filled width
                .build();

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        ImageView imageViewURL = new ImageView(findByZipCode.this);
        imageViewURL.setMinimumHeight(75);
        imageViewURL.setMinimumWidth(75);
        String imageURL = eventArgs2.get(2).getImageURL();
        if(imageURL != "None")
        {
            String url = imageURL;
            ImageLoader.getInstance().displayImage(imageURL, imageViewURL);
        }
        //This is the image ending.

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(eventName);
        builder.setView(imageViewURL);
        builder.setMessage(eventText)
                .setPositiveButton("Save to the Event", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Time to make an event. Get me to the dialog box.
                        Intent myIntent = new Intent(findByZipCode.this, CurrentPlan.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("eventNum",theEventNumber);
                        myIntent.putExtras(bundle);
                        if(eventArgs.size() != 0)
                        {
                            //Where does it go?
                            DefaultPlan theDefaultPlan = new DefaultPlan();
                            Event theEvent = eventArgs2.elementAt(2);//My event.
                            if(theEventNumber == 1)
                                theDefaultPlan.setEvent1(eventArgs2.elementAt(2));
                            else if(theEventNumber == 2)
                                theDefaultPlan.setEvent2(eventArgs2.elementAt(2));
                            else if(theEventNumber == 3)
                                theDefaultPlan.setEvent3(eventArgs2.elementAt(2));
                        }
                        startActivity(myIntent);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
//                .setNeutralButton("Learn More",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User wants to know more
//                        Intent myIntent = new Intent(suggestOnGPS.this, Recommendation.class);
//                        Bundle myBundle = new Bundle();
//                        myBundle.putString("imageURL",eventArgs2.get(0).getImageURL());
//                        myBundle.putString("name",eventArgs2.get(0).getEventName());
//                        myBundle.putString("address", eventArgs2.get(0).getEventAddress());
//                        myBundle.putString("theCLL",myCll);
//                        myBundle.putInt("eventNumber", theEventNumber);
//                        myIntent.putExtras(myBundle);
//                        startActivity(myIntent);
//                        //Need to put image url and plan inside the bundle.
//                    }
//                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    //*****************************************************************
    public class MetYelp extends AsyncTask<String, String, String> {
        public AsyncResponse delegate = null;
        @Override
        protected String doInBackground(String... params) {

            String myTerm = params[0];  //"Ramen";
            String theCLL = params[1];  //"34.052235, -118.243683";
            String theBody = "";
            while(theBody == null || theBody =="")
            {
                //YelpAPICLI yelpApiCli = new YelpAPICLI();
                //new JCommander(yelpApiCli, );
                Event myNewEvent = new Event();
                YelpAPI yelpAPI = new YelpAPI();
                //Here is the body.
                theBody = yelpAPI.queryAPIZ(yelpAPI, myTerm, theCLL, myNewEvent);

            }
            return theBody;    //The event. Call method set get.
/*
        catch (Exception e) {
            String theError = e.getLocalizedMessage();
            return "Failed";
        }*/
        }

        @Override
        protected void onPostExecute(String body) {
            delegate.processFinish(body);
        }
    }
    public void useZipYelp(String myTerm, NetYelp theNetYelp)
    {

        theNetYelp.delegate = this;

        TextView textView = (TextView)findViewById(R.id.etLocation);
        String indicator = "zip";
        if(textView.toString() != null) {
            String location = textView.getText().toString();
            theNetYelp.execute(myTerm, location,indicator);  //Here's the fail
        }
    }
    public String printIt()
    {
        if(args.size() != 0)
            return args.firstElement();
        else
            return "nothing";
    }
    public void recommendClick(String cll) {
        //Have the cll need yelp object.
        //Initializes this with the keys.

        //WHAT TO DO?
        //API needs stuff for method.

        //Need on click listener
        final String myCll = cll;
        Button myRecommendBtn = (Button) findViewById(R.id.btnYelpTerm);
        TextView textTerm = (TextView) findViewById(R.id.etTerm);
        final TextView textLocation = (TextView) findViewById(R.id.etLocation);

        myRecommendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                YelpAPI testingApi = new YelpAPI();
                if (true) {
                    EditText theTerm = (EditText) findViewById(R.id.etTerm);
                    String myTerm = theTerm.getText().toString();
                    String myLocation = textLocation.getText().toString();
                    if ((myTerm == null) || (myTerm.equals(""))) {
                        Toast.makeText(getApplicationContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
                    }
                    else if(myLocation == null || (myLocation.equals("")))
                    {
                        Toast.makeText(getApplicationContext(), "Please enter a location term", Toast.LENGTH_SHORT).show();

                    }

                    else {
                        //Need to use it more than once.
                        NetYelp theNetYelp = new NetYelp();
                        useZipYelp(myTerm, theNetYelp);
                        String resultEvent = printIt();
                        String findIt = resultEvent;
                    }
                }
                String well = printIt();
                //textViewRecEvent.setText(resultEvent.printTheEvent());
            }

        });

    }
    @Override
    public void processFinish(String output) {
        //Here is the item...
        args.add(output);
        YelpAPI theYelp = new YelpAPI();
        Event theEvent = new Event();
        Vector<Event> theEvents = new Vector<Event>();
        //Vector<Event> theBusinesses = new Vector<Event>();
        theYelp.queryAPIJSON(output, theEvent, theEvents);
        String beleiveMe = theEvent.printTheEvent();
        eventArgs.add(0,theEvent);
        eventArgs2.add(0,theEvents.get(0));
        eventArgs2.add(1, theEvents.get(1));
        eventArgs2.add(2, theEvents.get(2));
        String beleiveMe2 = eventArgs2.get(1).printTheEvent();
        String beleiveMe3 = eventArgs2.get(2).printTheEvent();

        TextView myTextView = (TextView) findViewById(R.id.tvRecEvent1Zip);
        myTextView.setText(beleiveMe);
        TextView myTextView2 = (TextView) findViewById(R.id.tvRecEvent2Zip);
        myTextView2.setText(beleiveMe2);
        TextView myTextView3 = (TextView) findViewById(R.id.tvRecEvent3Zip);
        myTextView3.setText(beleiveMe3);
        String test = "Test";

    }

}