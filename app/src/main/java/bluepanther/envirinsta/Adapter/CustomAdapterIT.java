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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bluepanther.envirinsta.Timeline.Indi_tab;
import bluepanther.envirinsta.InternalStorage.Internal_Audio;
import bluepanther.envirinsta.InternalStorage.Internal_File;
import bluepanther.envirinsta.InternalStorage.Internal_Image;
import bluepanther.envirinsta.InternalStorage.Internal_Text;
import bluepanther.envirinsta.InternalStorage.Internal_Video;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Timeline.TimeContent;


public class CustomAdapterIT extends RecyclerView.Adapter<CustomAdapterIT.MyViewHolder> {

 //   private ArrayList<DataModel> dataSet;
    public static Indi_tab mainActivity;
    Context context;
    String file1;
    List<RowItem> rowItems;
    List<RowItem> grpcontent;
    List<RowItem> percontent;
    List<RowItem> imgcontent = new ArrayList<>();
    List<RowItem> audcontent = new ArrayList<>();
    List<RowItem> vidcontent = new ArrayList<>();
    List<RowItem> filecontent = new ArrayList<>();
    List<RowItem> textcontent = new ArrayList<>();

    List<RowItem> imgcontent2 = new ArrayList<>();
    List<RowItem> audcontent2 = new ArrayList<>();
    List<RowItem> vidcontent2 = new ArrayList<>();
    List<RowItem> filecontent2 = new ArrayList<>();
    List<RowItem> textcontent2 = new ArrayList<>();
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

    public CustomAdapterIT(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(Indi_tab.myOnClickListener);
      //  view.setOnLongClickListener(Indi_tab.myOnLongClickListener);


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


                        if (TimeContent.percontent.get(listPosition).getType().equals("Images")){
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Images");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                        if (TimeContent.percontent.get(listPosition).getType().equals("Audios"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Audios");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                        if (TimeContent.percontent.get(listPosition).getType().equals("Videos"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Videos");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                        if (TimeContent.percontent.get(listPosition).getType().equals("Files"))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Files");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                        if (TimeContent.percontent.get(listPosition).getType().equals("Text"))
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

                        if (TimeContent.percontent.get(listPosition).getType().equals("Images")) {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                        if (TimeContent.percontent.get(listPosition).getType().equals("Audios"))
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
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                        if (TimeContent.percontent.get(listPosition).getType().equals("Videos"))
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
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                        if (TimeContent.percontent.get(listPosition).getType().equals("Files"))
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
                                    final String res = TimeContent.percontent.get(listPosition).getAuthor() + TimeContent.percontent.get(listPosition).getTime();
                                    System.out.println("Downloading" + res);
                                    final String ff = TimeContent.percontent.get(listPosition).getMember_name();

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
                    case 1:
                        if (TimeContent.percontent.get(listPosition).getType().equals("Text")) {
                            Internal_Text textobj;
                            try {

                                List<RowItem> textcontent;

                                String file = Environment.getExternalStorageDirectory() + "/text1.tmp";
                                File f = new File(file);
                                if (f.exists()) {
                                    System.out.println("FILE CREATING1");
                                    FileInputStream fis = new FileInputStream(file);
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    textobj = (Internal_Text) ois.readObject();
                                    ois.close();
                                    textcontent = textobj.textcontent;

                                    for (int j = 0; j < textcontent.size(); j++) {
                                        if (textcontent.get(j).getMember_name().equals(TimeContent.percontent.get(listPosition).getMember_name())) {
                                            textcontent.remove(j);
                                        }
                                    }

                                    for (int k = 0; k < textcontent.size(); k++) {
                                        System.out.println("List iteams are now" + textcontent.get(k).getMember_name());
                                    }
                                    new PrintWriter(file).close();
                                    String file1 = Environment.getExternalStorageDirectory() + "/text1.tmp";
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new Internal_Text(textcontent));
                                    oos.close();
                                    getlocalfiles();
                                    TimeContent.percontent=new ArrayList<RowItem>(percontent);
                                    CustomAdapterIT adapter2 = new CustomAdapterIT(context, percontent);
                                    Indi_tab.setContent(adapter2);

                                }

                            } catch (Exception e) {
                                System.out.println("EXCEPTION OCCOURED" + e);
                            }
                        }
                        else if (TimeContent.percontent.get(listPosition).getType().equals("Images")) {
                            Internal_Image textobj;
                            try {

                                List<RowItem> textcontent;

                                String file = Environment.getExternalStorageDirectory() + "/img1.tmp";
                                File f = new File(file);
                                if (f.exists()) {
                                    System.out.println("FILE CREATING1");
                                    FileInputStream fis = new FileInputStream(file);
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    textobj = (Internal_Image) ois.readObject();
                                    ois.close();
                                    textcontent = textobj.imgcontent;
                                    for (int j = 0; j < textcontent.size(); j++) {
                                        if (textcontent.get(j).getMember_name().equals(TimeContent.percontent.get(listPosition).getMember_name())) {
                                            textcontent.remove(j);
                                        }
                                    }
                                    for (int k = 0; k < textcontent.size(); k++) {
                                        System.out.println("List iteams are now" + textcontent.get(k).getMember_name());
                                    }
                                    new PrintWriter(file).close();
                                    String file1 = Environment.getExternalStorageDirectory() + "/img1.tmp";
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new Internal_Image(textcontent));
                                    oos.close();
                                    getlocalfiles();
                                    TimeContent.percontent=new ArrayList<RowItem>(percontent);
                                    CustomAdapterIT adapter2 = new CustomAdapterIT(context, percontent);
                                    Indi_tab.setContent(adapter2);
                                }

                            } catch (Exception e) {
                                System.out.println("EXCEPTION OCCOURED" + e);
                            }


                        }
                        else if (TimeContent.percontent.get(listPosition).getType().equals("Audios")) {
                            Internal_Audio textobj;
                            try {

                                List<RowItem> textcontent;

                                String file = Environment.getExternalStorageDirectory() + "/aud1.tmp";
                                File f = new File(file);
                                if (f.exists()) {
                                    System.out.println("FILE CREATING1");
                                    FileInputStream fis = new FileInputStream(file);
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    textobj = (Internal_Audio) ois.readObject();
                                    ois.close();
                                    textcontent = textobj.audiocontent;
                                    for (int j = 0; j < textcontent.size(); j++) {
                                        if (textcontent.get(j).getMember_name().equals(TimeContent.percontent.get(listPosition).getMember_name())) {
                                            textcontent.remove(j);
                                        }
                                    }

                                    for (int k = 0; k < textcontent.size(); k++) {
                                        System.out.println("List iteams are now" + textcontent.get(k).getMember_name());
                                    }
                                    new PrintWriter(file).close();
                                    String file1 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new Internal_Audio(textcontent));
                                    oos.close();
                                    getlocalfiles();
                                    TimeContent.percontent=new ArrayList<RowItem>(percontent);
                                    CustomAdapterIT adapter2 = new CustomAdapterIT(context, percontent);
                                    Indi_tab.setContent(adapter2);

                                }

                            } catch (Exception e) {
                                System.out.println("EXCEPTION OCCOURED" + e);
                            }
                        }
                        else if (TimeContent.percontent.get(listPosition).getType().equals("Videos")) {
                            Internal_Video textobj;
                            try {

                                List<RowItem> textcontent;

                                String file = Environment.getExternalStorageDirectory() + "/vid1.tmp";
                                File f = new File(file);
                                if (f.exists()) {
                                    System.out.println("FILE CREATING1");
                                    FileInputStream fis = new FileInputStream(file);
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    textobj = (Internal_Video) ois.readObject();
                                    ois.close();
                                    textcontent = textobj.videocontent;
                                    for (int j = 0; j < textcontent.size(); j++) {
                                        if (textcontent.get(j).getMember_name().equals(TimeContent.percontent.get(listPosition).getMember_name())) {
                                            textcontent.remove(j);
                                        }
                                    }

                                    for (int k = 0; k < textcontent.size(); k++) {
                                        System.out.println("List iteams are now" + textcontent.get(k).getMember_name());
                                    }
                                    new PrintWriter(file).close();
                                    String file1 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new Internal_Video(textcontent));
                                    oos.close();
                                    getlocalfiles();
                                    TimeContent.percontent=new ArrayList<RowItem>(percontent);
                                    CustomAdapterIT adapter2 = new CustomAdapterIT(context, percontent);
                                    Indi_tab.setContent(adapter2);

                                }

                            } catch (Exception e) {
                                System.out.println("EXCEPTION OCCOURED" + e);
                            }
                        }
                        else if (TimeContent.percontent.get(listPosition).getType().equals("Files")) {
                            Internal_File textobj;
                            try {

                                List<RowItem> textcontent;

                                String file = Environment.getExternalStorageDirectory() + "/file1.tmp";
                                File f = new File(file);
                                if (f.exists()) {
                                    System.out.println("FILE CREATING1");
                                    FileInputStream fis = new FileInputStream(file);
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    textobj = (Internal_File) ois.readObject();
                                    ois.close();
                                    textcontent = textobj.filecontent;
                                    for (int j = 0; j < textcontent.size(); j++) {
                                        if (textcontent.get(j).getMember_name().equals(TimeContent.percontent.get(listPosition).getMember_name())) {
                                            textcontent.remove(j);
                                        }
                                    }

                                    for (int k = 0; k < textcontent.size(); k++) {
                                        System.out.println("List iteams are now" + textcontent.get(k).getMember_name());
                                    }
                                    new PrintWriter(file).close();
                                    String file1 = Environment.getExternalStorageDirectory() + "/file1.tmp";
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new Internal_File(textcontent));
                                    oos.close();
                                    getlocalfiles();
                                    TimeContent.percontent=new ArrayList<RowItem>(percontent);
                                    CustomAdapterIT adapter2 = new CustomAdapterIT(context, percontent);
                                    Indi_tab.setContent(adapter2);

                                }

                            } catch (Exception e) {
                                System.out.println("EXCEPTION OCCOURED" + e);
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
//            holder.checkBox.setVisibility(View.VISIBLE);
//            holder.checkBox.setChecked(false);
//        }


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public void getlocalfiles()
    {
        final Internal_Image imgobj;
        String filei = Environment.getExternalStorageDirectory() + "/img.tmp";
        System.out.println("FILE READING");
        FileInputStream fisi = null;
        try {
            fisi = new FileInputStream(filei);
            ObjectInputStream ois = new ObjectInputStream(fisi);
            imgobj = (Internal_Image) ois.readObject();
            ois.close();
            imgcontent = imgobj.imgcontent;
            Collections.reverse(imgcontent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Audio audobj;
        String filea = Environment.getExternalStorageDirectory() + "/aud.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisa = null;
        try {
            fisa = new FileInputStream(filea);
            ObjectInputStream ois = new ObjectInputStream(fisa);
            audobj = (Internal_Audio) ois.readObject();
            ois.close();
            audcontent = audobj.audiocontent;
            Collections.reverse(audcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Text txtobj;
        String filet = Environment.getExternalStorageDirectory() + "/text.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fist = null;
        try {
            fist = new FileInputStream(filet);
            ObjectInputStream ois = new ObjectInputStream(fist);
            txtobj = (Internal_Text) ois.readObject();
            ois.close();
            textcontent = txtobj.textcontent;
            Collections.reverse(textcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Video vidobj;
        String filev = Environment.getExternalStorageDirectory() + "/vid.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisv = null;
        try {
            fisv = new FileInputStream(filev);
            ObjectInputStream ois = new ObjectInputStream(fisv);
            vidobj = (Internal_Video) ois.readObject();
            ois.close();
            vidcontent = vidobj.videocontent;
            Collections.reverse(vidcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_File fileobj;
        String filef = Environment.getExternalStorageDirectory() + "/file.tmp";
        System.out.println("FILE READING");
        FileInputStream fisf = null;
        try {
            fisf = new FileInputStream(filef);
            ObjectInputStream ois = new ObjectInputStream(fisf);
            fileobj = (Internal_File) ois.readObject();
            ois.close();
            filecontent = fileobj.filecontent;
            Collections.reverse(filecontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Image imgobj2;
        String filei2 = Environment.getExternalStorageDirectory() + "/img1.tmp";
        System.out.println("FILE READING");
        FileInputStream fisi2 = null;
        try {
            fisi2 = new FileInputStream(filei2);
            ObjectInputStream ois = new ObjectInputStream(fisi2);
            imgobj2 = (Internal_Image) ois.readObject();
            ois.close();
            imgcontent2 = imgobj2.imgcontent;
            Collections.reverse(imgcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Audio audobj2;
        String filea2 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisa2 = null;
        try {
            fisa2 = new FileInputStream(filea2);
            ObjectInputStream ois = new ObjectInputStream(fisa2);
            audobj2 = (Internal_Audio) ois.readObject();
            ois.close();
            audcontent2 = audobj2.audiocontent;
            Collections.reverse(audcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Text txtobj2;
        String filet2 = Environment.getExternalStorageDirectory() + "/text1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fist2 = null;
        try {
            fist2 = new FileInputStream(filet2);
            ObjectInputStream ois = new ObjectInputStream(fist2);
            txtobj2 = (Internal_Text) ois.readObject();
            ois.close();
            textcontent2 = txtobj2.textcontent;
            Collections.reverse(textcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Video vidobj2;
        String filev2 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisv2 = null;
        try {
            fisv2 = new FileInputStream(filev2);
            ObjectInputStream ois = new ObjectInputStream(fisv2);
            vidobj2 = (Internal_Video) ois.readObject();
            ois.close();
            vidcontent2 = vidobj2.videocontent;
            Collections.reverse(vidcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_File fileobj2;
        String filef2 = Environment.getExternalStorageDirectory() + "/file1.tmp";
        System.out.println("FILE READING");
        FileInputStream fisf2 = null;
        try {
            fisf2 = new FileInputStream(filef2);
            ObjectInputStream ois = new ObjectInputStream(fisf2);
            fileobj2 = (Internal_File) ois.readObject();
            ois.close();
            filecontent2 = fileobj2.filecontent;
            Collections.reverse(filecontent2);


        } catch (Exception e) {
            e.printStackTrace();
        }
        grpcontent = new ArrayList<>();
        grpcontent.addAll(textcontent);
        grpcontent.addAll(imgcontent);
        grpcontent.addAll(audcontent);
        grpcontent.addAll(vidcontent);
        grpcontent.addAll(filecontent);
        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(grpcontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });
        percontent = new ArrayList<>();
        percontent.addAll(textcontent2);
        percontent.addAll(imgcontent2);
        percontent.addAll(audcontent2);
        percontent.addAll(vidcontent2);
        percontent.addAll(filecontent2);
        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(percontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });


    }
}
