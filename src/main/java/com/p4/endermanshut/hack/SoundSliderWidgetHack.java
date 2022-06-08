package com.p4.endermanshut.hack;

import static com.p4.endermanshut.ConfigInit.endermanVolume;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class SoundSliderWidgetHack extends SliderWidget {
    public SoundSliderWidgetHack(int x, int y, int width, int height, Text text, double value) {
        super(x, y, width, height, text, value);
    }
    protected void updateMessage() {
        Text text = (float)this.value == (float)this.getYImage(false) ? ScreenTexts.OFF : Text.translatable((int)(this.value * 100.0D) + "%");
        this.setMessage((Text.translatable("endermanshut.text.screams")).append(text));
    }

    public void applyValue() {
        endermanVolume = this.value;
    }
}
