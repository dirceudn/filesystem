package com.sample.dirceu.limatest.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.dirceu.limatest.R;
import com.sample.dirceu.limatest.interfaces.RecyclerViewClickListener;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> implements Filterable {


    List<Node> nodeList;
    List<Node> nodeListFilterable;
    RecyclerViewClickListener recyclerViewClickListener;

    public NodeAdapter(List<Node> list, RecyclerViewClickListener listener) {
        this.nodeList = list;
        this.nodeListFilterable = list;
        this.recyclerViewClickListener = listener;

    }

    @NonNull
    @Override
    public NodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_node,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeAdapter.ViewHolder holder, int position) {
        Node currentNode = nodeListFilterable.get(position);
        holder.textViewName.setText(currentNode.getName());
        holder.textViewSize.setText(Utility.getFileSize(currentNode.getSize()));
        holder.textViewDate.setText(Utility.convertTimeStampToDate(currentNode.getNotification_time()));
        setImageIconWithType(currentNode.getMimetype(), holder);


    }

    private void setImageIconWithType(String mimetype, ViewHolder holder) {
        switch (mimetype) {
            case "inode/directory":
                holder.imageViewFile.setImageResource(R.drawable.ic_folder_default_24dp);
                holder.textViewSize.setText(R.string.str_directory);
                holder.imageViewOptions.setVisibility(View.GONE);
                break;
            case "image/png":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_png_24dp);
                break;
            case "image/jpeg":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_jpeg_24dp);
                break;
            case "image/jpg":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_jpeg_24dp);
                break;
            case "image/gif":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_gif_24dp);
                break;
            case "video/mp4":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_mp4_24dp);
                break;
            case "audio/mpeg":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_mpg_24dp);
                break;
            case "text/plain":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_text_24dp);
                break;
            case "application/zip":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_zip_24dp);
                break;
            case "application/pdf":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_pdf_24dp);
                break;
            case "application/vnd.ms-powerpoint":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_ppt_24dp);
                break;
            case "application/vnd.ms-excel":
                holder.imageViewFile.setImageResource(R.drawable.ic_file_xls_24dp);
                break;
            default:
                holder.imageViewFile.setImageResource(R.drawable.ic_file_default_24dp);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return nodeListFilterable.size();
    }

    public List<Node> getNodeList() {
        return nodeListFilterable;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    nodeListFilterable = nodeList;
                } else {
                    ArrayList<Node> filteredList = new ArrayList<>();
                    for (Node row : nodeList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getMimetype().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    nodeListFilterable = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = nodeListFilterable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                nodeListFilterable = (ArrayList<Node>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageViewFile;
        private ImageView imageViewOptions;
        private TextView textViewName;
        private TextView textViewSize;
        private TextView textViewDate;

        ViewHolder(View itemView) {
            super(itemView);


            imageViewFile = itemView.findViewById(R.id.image_file);
            imageViewOptions = itemView.findViewById(R.id.image_more);
            textViewSize = itemView.findViewById(R.id.text_size);
            textViewDate = itemView.findViewById(R.id.text_date);
            textViewName = itemView.findViewById(R.id.text_name);
            imageViewOptions.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.node_layout:
                    if (recyclerViewClickListener != null) {
                        recyclerViewClickListener.recyclerViewClick(view, this.getLayoutPosition(), null);
                    }
                    break;
                case R.id.image_more:
                    if (recyclerViewClickListener != null) {
                        recyclerViewClickListener.recyclerViewClick(view, this.getLayoutPosition(), "OPTIONS");
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
