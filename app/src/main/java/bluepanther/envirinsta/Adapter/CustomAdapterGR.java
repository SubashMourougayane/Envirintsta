package bluepanther.envirinsta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.io.File;
import java.util.List;

import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Repgroup_tab;
import bluepanther.envirinsta.Timeline.TimeContent;


public class CustomAdapterGR extends RecyclerView.Adapter<CustomAdapterGR.MyViewHolder> {

 //   private ArrayList<DataModel> dataSet;
    public static Repgroup_tab mainActivity;
    Context context;
    String file1;
    List<RowItem> rowItems;
    public static class MyViewHolder extends RecyclerView.ViewHolder
            {

        TextView title, category, uploader,date;
        CheckBox checkBox;
        ImageView imageViewIcon;
        CardView cardView;
                BoomMenuButton bmb1;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.category = (TextView) itemView.findViewById(R.id.category);
            this.uploader = (TextView) itemView.findViewById(R.id.uploader);
            this.date=(TextView)itemView.findViewById(R.id.date);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView99);
            this.bmb1 = (BoomMenuButton) itemView.findViewById(R.id.bmb1);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            //cardView.setOnLongClickListener(mainActivity);

        }


    }

    public CustomAdapterGR(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(Repgroup_tab.myOnClickListener);
      //  view.setOnLongClickListener(Group_tab.myOnLongClickListener);


        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView title = holder.title;
        TextView category = holder.category;
        TextView uploader = holder.uploader;
        TextView date=holder.date;
        ImageView imageView = holder.imageViewIcon;
        RowItem row_pos = rowItems.get(listPosition);
        BoomMenuButton bmb1 = holder.bmb1;

        title.setText(row_pos.getMember_name());
        category.setText(row_pos.getStatus());
        uploader.setText(row_pos.getAuthor());
        date.setText(row_pos.getTime());
        imageView.setImageResource(row_pos.getProfile_pic_id());

        holder.bmb1.clearBuilders();
        for (int i = 0; i < holder.bmb1.getPiecePlaceEnum().pieceNumber(); i++)
            holder.bmb1.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());

        bmb1.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                System.out.println("CLicked at"+index);
                switch(index)
                {
                    case 0:
                        String str = "File Saved to Internal Storage";


                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Images")){
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Images");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    String file1 = Environment.getExternalStorageDirectory() + "/Soul/Images/" + ff + ".jpg";
                                    File files = new File(file1);
                                    storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            System.out.println("sad" + exception);
                                        }
                                    });


                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }
                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Audios"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Audios");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    String file1 = Environment.getExternalStorageDirectory() + "/Soul/Audios/" + ff + ".mp3";
                                    File files = new File(file1);
                                    storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            System.out.println("sad" + exception);
                                        }
                                    });


                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }
                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Videos"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Videos");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    String file1 = Environment.getExternalStorageDirectory() + "/Soul/Videos/" + ff + ".mp4";
                                    File files = new File(file1);
                                    storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            System.out.println("sad" + exception);
                                        }
                                    });


                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }
                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Files"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Files");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                        @Override
                                        public void onSuccess(StorageMetadata storageMetadata) {
                                            System.out.println("Type is" + storageMetadata.getContentType() + "end");
                                            String splitarr[] = storageMetadata.getContentType().split("/");
                                            System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                                            final String ftype = storageMetadata.getContentType();


                                            if (ftype.contains("pdf")) {
                                                file1 = Environment.getExternalStorageDirectory() + "/" + "pdf.pdf";
                                            } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
                                                file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";

                                            } else if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
                                                file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                                            } else if (ftype.equals("presentation")) {
                                                file1 = Environment.getExternalStorageDirectory() + "/" + "present.ppt";

                                            } else if (ftype.equals("spreadsheet") || ftype.equals("sheet")) {
                                                file1 = Environment.getExternalStorageDirectory() + "/" + "excel.xls";

                                            }
                                            final File files = new File(file1);


                                            storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    //    Toast.makeText(context,"File has been sevaed",Toast.LENGTH_SHORT).show();
                                                }
                                            });



                                        }
                                    });



                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }
                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Text"))
                        {

                        }

                        Toast.makeText((context), str, Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText((context), "Loading File", Toast.LENGTH_SHORT).show();
//                                                                        progressDialog = new ProgressDialog(context);
//                                                                        progressDialog.setTitle("Downloading");
//                                                                        progressDialog.setMessage("Downloading File");
//                                                                        progressDialog.show();

                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Images")) {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".jpg";
                                    final File files = new File(file1);
                                    // final File tf = File.createTempFile("sample","txt");
                                    storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                            Uri screenshotUri = Uri.fromFile(files);

                                            sharingIntent.setType("image/png");
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                            context.startActivity(Intent.createChooser(sharingIntent, "Share content using").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));// "Share image using"));
                                            //     progressDialog.dismiss();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            System.out.println("sad" + exception);
                                        }
                                    });


                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }
                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Audios"))
                        {
//                                                                          progressDialog = new ProgressDialog(context);
//                                                                          progressDialog.setTitle("Downloading");
//                                                                          progressDialog.setMessage("Downloading File");
//                                                                          progressDialog.show();
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".mp3";
                                    final File files = new File(file1);
                                    // final File tf = File.createTempFile("sample","txt");
                                    storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                            Uri screenshotUri = Uri.fromFile(files);

                                            sharingIntent.setType("audio/mp3");
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                            context.startActivity(Intent.createChooser(sharingIntent, "Share content using").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));// "Share Audio using"));
                                            //      progressDialog.dismiss();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            System.out.println("sad" + exception);
                                        }
                                    });


                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }
                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Videos"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
//                                                                                  progressDialog = new ProgressDialog(context);
//                                                                                  progressDialog.setTitle("Downloading");
//                                                                                  progressDialog.setMessage("Downloading Video");
//                                                                                  progressDialog.show();
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".mp4";
                                    final File files = new File(file1);
                                    // final File tf = File.createTempFile("sample","txt");
                                    storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                            Uri screenshotUri = Uri.fromFile(files);

                                            sharingIntent.setType("video/mp4");
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);

                                            context.startActivity(Intent.createChooser(sharingIntent, "Share content using").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));// "Share Video using"));
                                            //     progressDialog.dismiss();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            System.out.println("sad" + exception);
                                        }
                                    });


                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }
                        if (TimeContent.grpcontent.get(listPosition).getType().equals("Files"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
//                                                                                  progressDialog = new ProgressDialog(context);
//                                                                                  progressDialog.setTitle("Downloading");
//                                                                                  progressDialog.setMessage("Downloading File");
//                                                                                  progressDialog.show();
                                    final String res = TimeContent.grpcontent.get(listPosition).getAuthor() + TimeContent.grpcontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.grpcontent.get(listPosition).getMember_name();

                                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                    System.out.println("Storage refference : " + storageReference);

                                    storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                        @Override
                                        public void onSuccess(StorageMetadata storageMetadata) {
                                            System.out.println("Type is" + storageMetadata.getContentType() + "end");
                                            String splitarr[] = storageMetadata.getContentType().split("/");
                                            System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                                            final String ftype = storageMetadata.getContentType();

                                            if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword")){                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                                            } else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml")) {
                                                file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";
                                            } else if(ftype.contains("pdf")){
                                                file1 = Environment.getExternalStorageDirectory() + "/" + "pdf." + splitarr[splitarr.length - 1];

                                            }
                                            final File files = new File(file1);

                                            storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                    if(ftype.contains("pdf")){
                                                        sharingIntent.setDataAndType(Uri.fromFile(files), ftype);
                                                    }else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml"))
                                                    {
                                                        sharingIntent.setDataAndType(Uri.fromFile(files), "text/plain");

                                                    }else if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword"))
                                                    {
                                                        sharingIntent.setDataAndType(Uri.fromFile(files), "application/msword");

                                                    }else if(ftype.equals("presentation"))
                                                    {
                                                        sharingIntent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-powerpoint");

                                                    }
                                                    else if(ftype.equals("spreadsheet")||ftype.equals("sheet"))
                                                    {
                                                        sharingIntent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-excel");

                                                    }
                                                    context.startActivity(Intent.createChooser(sharingIntent, "Share content using").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));// "Share File using"));
                                                    //    progressDialog.dismiss();



                                                }
                                            });

                                        }
                                    });



                                } catch (Exception e) {
                                    System.out.println("Errror in file" + e);
                                }
                            } else {
                                // Do something else on failure
                            }
                        }



                        break;
                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
//        if(!mainActivity.is_in_action_mode)
//        {
//            holder.checkBox.setVisibility(View.GONE);
//        }
//        else
//        {
//
//            holder.checkBox.setVisibility(View.VISIBLE);
//            holder.checkBox.setChecked(false);
//        }


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }
}
