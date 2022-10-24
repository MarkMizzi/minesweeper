package minesweeper;

import java.util.ArrayList;
import java.util.List;

public abstract class Model {
    private List<View> views = new ArrayList<>();

    public void attach(View view) {
        this.views.add(view);
    }

    public void detach(View view) {
        this.views.remove(view);
    }

    public void notifyViews() {
        for (View view : views) {
            view.update();
        }
    }
}
