package anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place;

import androidx.lifecycle.ViewModel;

public class PlaceViewModel extends ViewModel {
    public class Place {

        public String name, description;
        public double latitude, longitude;

        public Place() {}

        public Place(String name, String description, double lat, double lng) {
            this.name = name;
            this.description = description;
            this.latitude = lat;
            this.longitude = lng;
        }
    }
}