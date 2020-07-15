package com.ar.salata.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.salata.R;
import com.ar.salata.repositories.model.UserAddress;
import com.ar.salata.ui.activities.UserProfileActivity;
import com.ar.salata.ui.fragments.OrderEditConfirmationDialogFragment;
import com.ar.salata.ui.utils.ArabicString;

import java.util.ArrayList;
import java.util.List;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.AddressViewHolder> {
    private Context context;
    private List<UserAddress> addresses;

    public AddressRecyclerAdapter(Context context) {
        this.context = context;
        addresses = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_rv_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.address.setText(ArabicString.toArabic(addresses.get(position).getZone() + " / " + addresses.get(position).getAddress()));
        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderEditConfirmationDialogFragment confirmationDialogFragment =
                        OrderEditConfirmationDialogFragment.newInstance("تأكيد الحذف",
                                "هل تود حذف العنوان \n" + ArabicString.toArabic(addresses.get(position).getZone() + " / " + addresses.get(position).getAddress()),
                                addresses.get(position).getAddressId());
                confirmationDialogFragment.show(((UserProfileActivity) context).getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public void setData(List<UserAddress> addresses) {
        this.addresses.clear();
        this.addresses.addAll(addresses);
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        private ImageButton deleteAddress;
        private TextView address;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteAddress = itemView.findViewById(R.id.ib_delete_address);
            address = itemView.findViewById(R.id.tv_user_address);
        }
    }
}
