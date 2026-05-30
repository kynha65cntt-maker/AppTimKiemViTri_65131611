package anky.ntu.edu.duanck_apptimkiemvitri_65131611;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place.Place;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

        private final List<Place> placeList;

    public PlaceAdapter(List<Place> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placeList.get(position);

        holder.txtPlaceName.setText(place.name);
        holder.txtPlaceDesc.setText(place.description);
        holder.txtPlaceLatLng.setText("Lat: " + place.latitude + " | Lng: " + place.longitude);

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(),
                    "Bạn chọn: " + place.name,
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {

        TextView txtPlaceName, txtPlaceDesc, txtPlaceLatLng;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPlaceName = itemView.findViewById(R.id.txtPlaceName);
            txtPlaceDesc = itemView.findViewById(R.id.txtPlaceDesc);
            txtPlaceLatLng = itemView.findViewById(R.id.txtPlaceLatLng);
        }
    }
}