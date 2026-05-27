package anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place;

import androidx.lifecycle.ViewModel;

public class PlaceViewModel extends ViewModel {
    public class Place {

        public String name, description;
        public double latitude, longitude;

        public Place() {}

        public Place(String name, String description, double latitude, double  longitude) {
            this.name = name;
            this.description = description;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}