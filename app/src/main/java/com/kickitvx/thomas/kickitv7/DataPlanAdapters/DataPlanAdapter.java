package com.kickitvx.thomas.kickitv7.DataPlanAdapters;

/**
 * Created by Thomas on 4/23/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickitvx.thomas.kickitv7.CurrentPlan;
import com.kickitvx.thomas.kickitv7.DefaultPlan;
import com.kickitvx.thomas.kickitv7.LoadPlans;
import com.kickitvx.thomas.kickitv7.Main_Menu;
import com.kickitvx.thomas.kickitv7.Plan;
import com.kickitvx.thomas.kickitv7.R;
import com.kickitvx.thomas.kickitv7.database.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataPlanAdapter extends RecyclerView.Adapter<DataPlanAdapter.ViewHolder> {

    private List<Plan> myPlans;
    private Context myContext;

    public DataPlanAdapter(Context context, List<Plan> plans) {
        this.myContext = context;
        this.myPlans = plans;
    }

    @Override
    public DataPlanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View itemView = inflater.inflate(R.layout.list_plan, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataPlanAdapter.ViewHolder holder, int position) {
        Plan item = myPlans.get(position);
        final Plan myClickPlan = myPlans.get(position);
        final int theCurrentPosition = position;

        try {
            holder.tvName.setText(item.getPlanName());
            //String imageFile = item.getImage();
            //InputStream inputStream = mContext.getAssets().open(imageFile);
            //Drawable d = Drawable.createFromStream(inputStream, null);
            //holder.imageView.setImageDrawable(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Build a dialog box.
                //Offer two choices Load or Cancel.
                new AlertDialog.Builder(myContext)
                        .setTitle("Do you want to select this plan?")
                        .setMessage("Plan Name: " + myClickPlan.getPlanName() + "\n" +
                                myClickPlan.Event0.printTheEvent() + "\n" +
                                myClickPlan.Event1.printTheEvent() + "\n" +
                                myClickPlan.Event2.printTheEvent() + "\n")
                        .setPositiveButton("Load Plan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {

                                    DefaultPlan.planDefault = myClickPlan;
                                    Intent myIntent = new Intent(myContext, CurrentPlan.class);
                                    myContext.startActivity(myIntent);

                                }
                                catch (Exception e)
                                {

                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Current position. So...
                                try {
                                    DataSource myDataSource = new DataSource(myContext);
                                    myDataSource.open();
                                    String thePlanName = myClickPlan.getPlanName();
                                    myDataSource.deletePlan(thePlanName);
                                    Intent myIntent = new Intent(myContext, LoadPlans.class);
                                    myDataSource.close();
                                    myContext.startActivity(myIntent);

                                }
                                catch (Exception e)
                                {

                                }


                            }
                        }).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return myPlans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        //public ImageView imageView;
        public View myView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            //imageView = (ImageView) itemView.findViewById(R.id.imageView);
            myView = itemView;
        }
    }
}