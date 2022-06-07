package com.abdosalm.parks.data;

import com.abdosalm.parks.controller.AppController;
import com.abdosalm.parks.model.Activities;
import com.abdosalm.parks.model.EntranceFees;
import com.abdosalm.parks.model.Images;
import com.abdosalm.parks.model.OperatingHours;
import com.abdosalm.parks.model.Park;
import com.abdosalm.parks.model.StandardHours;
import com.abdosalm.parks.model.Topics;
import com.abdosalm.parks.util.Util;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void getParks(final AsyncResponse callback , String stateCode){
        String url = Util.getParksUrl(stateCode);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET , url, null , response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Park park =  new Park();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    park.setId(jsonObject.getString("id"));
                    park.setFullName(jsonObject.getString("fullName"));
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    park.setParkCode(jsonObject.getString("parkCode"));
                    park.setStates(jsonObject.getString("states"));

                    JSONArray imageList = jsonObject.getJSONArray("images");
                    List<Images> list = new ArrayList<>();
                    for (int j = 0; j < imageList.length(); j++) {
                        Images images = new Images();
                        images.setCredit(imageList.getJSONObject(j).getString("credit"));
                        images.setTitle(imageList.getJSONObject(j).getString("title"));
                        images.setUrl(imageList.getJSONObject(j).getString("url"));
                        list.add(images);
                    }
                    park.setImages(list);

                    //setup Activities
                    JSONArray activityArray = jsonObject.getJSONArray("activities");
                    List<Activities>activitiesList = new ArrayList<>();
                    for (int j = 0; j < activityArray.length(); j++) {
                        Activities activities = new Activities();
                        activities.setId(activityArray.getJSONObject(j).getString("id"));
                        activities.setName(activityArray.getJSONObject(j).getString("name"));
                        activitiesList.add(activities);
                    }
                    park.setActivities(activitiesList);

                    //Topics
                    JSONArray topicsArray = jsonObject.getJSONArray("topics");
                    List<Topics> topicsList = new ArrayList<>();
                    for (int j = 0; j < topicsArray.length(); j++) {
                        Topics topics = new Topics();
                        topics.setId(topicsArray.getJSONObject(j).getString("id"));
                        topics.setName(topicsArray.getJSONObject(j).getString("name"));
                        topicsList.add(topics);
                    }
                    park.setTopics(topicsList);

                    //operating hours
                    JSONArray opHours = jsonObject.getJSONArray("operatingHours");
                    List<OperatingHours> operatingHours = new ArrayList<>();
                    for (int j = 0; j < opHours.length(); j++) {
                        OperatingHours op =  new OperatingHours();
                        op.setDescription(opHours.getJSONObject(j).getString("description"));
                        StandardHours standardHours =  new StandardHours();
                        JSONObject hours = opHours.getJSONObject(j).getJSONObject("standardHours");

                        standardHours.setWednesday(hours.getString("wednesday"));
                        standardHours.setMonday(hours.getString("monday"));
                        standardHours.setThursday(hours.getString("thursday"));
                        standardHours.setSunday(hours.getString("sunday"));
                        standardHours.setTuesday(hours.getString("tuesday"));
                        standardHours.setFriday(hours.getString("friday"));
                        standardHours.setSaturday(hours.getString("saturday"));

                        op.setStandardHours(standardHours);

                        op.setName(opHours.getJSONObject(j).getString("name"));

                        operatingHours.add(op);
                    }
                    park.setOperatingHours(operatingHours);

                    park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                    park.setDesignation(jsonObject.getString("description"));

                    //Entrance fees
                    JSONArray entranceFeesArray = jsonObject.getJSONArray("entranceFees");
                    List<EntranceFees> entranceFeesList = new ArrayList<>();
                    for (int j = 0; j < entranceFeesArray.length(); j++) {
                        EntranceFees entranceFees = new EntranceFees();
                        entranceFees.setCost(entranceFeesArray.getJSONObject(j).getString("cost"));
                        entranceFees.setDescription(entranceFeesArray.getJSONObject(j).getString("description"));
                        entranceFees.setTitle(entranceFeesArray.getJSONObject(j).getString("title"));
                        entranceFeesList.add(entranceFees);
                    }
                    park.setEntranceFees(entranceFeesList);

                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));

                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    park.setName(jsonObject.getString("name"));
                    park.setDesignation(jsonObject.getString("designation"));
                    parkList.add(park);
                }
                if (null != callback){ callback.processPark(parkList); }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } , error -> error.printStackTrace());

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
