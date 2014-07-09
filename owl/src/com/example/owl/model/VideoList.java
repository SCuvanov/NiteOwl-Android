package com.example.owl.model;

import java.util.ArrayList;

public class VideoList {

    public static ArrayList<VideoItem> getVideoList() {
        ArrayList<VideoItem> resultList = new ArrayList<VideoItem>();

        VideoItem itm;

        itm = new VideoItem(
                1,
                89,
                "Bar",
                "bar.jpg",
                "Bar tonight @ 9. Drinks all night!",
                "9:00PM",
                true);
        resultList.add(itm);

        itm = new VideoItem(
                2,
                81,
                "Club",
                "club.jpg",
                "Lets go to the Club! Meet up @ 10.",
                "9:00PM",
                false);
        resultList.add(itm);

        itm = new VideoItem(
                3,
                86,
                "Wedding",
                "wedding.jpg",
                "Guess who is getting married? Wear your best Ladies and Gentleman.",
                "5:00PM",
                true);
        resultList.add(itm);


        itm = new VideoItem(
                4,
                85,
                "Scuba",
                "scuba.jpg",
                "BlubBlubBlub, time to see some fishies!",
                "11:00AM",
                false);
        resultList.add(itm);

        itm = new VideoItem(
                5,
                87,
                "Barbecue",
                "barbecue.jpg",
                "Brews and good food, cant beat that! Everyone show up around 6.",
                "6:00PM",
                false);
        resultList.add(itm);

        itm = new VideoItem(
                6,
                89,
                "Diner",
                "diner.jpg",
                "Breakfast with the Boys. Bacon eating contest anyone?",
                "9:00AM",
                false);
        resultList.add(itm);

        itm = new VideoItem(
                7,
                81,
                "Vegas",
                "vegas.jpg",
                "I am thinking Hangover Part 4. Btw, where is Doug?",
                null,
                false);
        resultList.add(itm);

        itm = new VideoItem(
                8,
                86,
                "Camping",
                "camping.jpg",
                "Lions, Tigers, and Bears... Oh My!",
                null,
                false);
        resultList.add(itm);

        itm = new VideoItem(
                9,
                85,
                "Movies",
                "movies.jpg",
                "Anyone want to go see the new Spaceballs 2 movie?",
                "8:45PM",
                false);
        resultList.add(itm);

        itm = new VideoItem(
                10,
                87,
                "Shopping",
                "shopping.jpg",
                "I'm gonna pop some tags, only got $20 in my pocket.",
                null,
                false);
        resultList.add(itm);


        return resultList;
    }

    ;

}
