package bluepanther.envirinsta.Adapter;


import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import bluepanther.envirinsta.R;


public class BuilderManager {

    private static int[] imageResources = new int[]{
            R.drawable.saveboom,
                R.drawable.deleteboom,
                    R.drawable.shareboom,
    };

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    static SimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
        return new SimpleCircleButton.Builder()
                .normalImageRes(getImageResource());
    }



    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }
}
