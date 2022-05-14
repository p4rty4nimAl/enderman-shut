package com.p4.endermanshut.hack;

import static com.p4.endermanshut.ConfigInit.endermanVolume;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SoundSliderWidgetHack extends SliderWidget {
    public SoundSliderWidgetHack(int x, int y, int width, int height, Text text, double value) {
        super(x, y, width, height, text, value);
    }
    protected void updateMessage() {
        Text text = (float)this.value == (float)this.getYImage(false) ? ScreenTexts.OFF : new TranslatableText((int)(this.value * 100.0D) + "%");
        this.setMessage((new TranslatableText("endermanshut.text.screams")).append(text));
    }

    public void applyValue() {
        endermanVolume = this.value;
    }
}
