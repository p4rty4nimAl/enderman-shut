package com.p4.endermanshut.mixin;

import com.p4.endermanshut.hack.SoundSliderWidgetHack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SoundSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.p4.endermanshut.ConfigInit.endermanVolume;
import static com.p4.endermanshut.ConfigInit.turnOffScreams;

@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin extends GameOptionsScreen {
    public SoundOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }
    @Inject(method = "init()V", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.clearChildren();
        this.addDrawableChild(new SoundSliderWidget(this.client, this.width / 2 - 155, this.height / 6 - 12, SoundCategory.MASTER, 310));
        int k = 2;
        SoundCategory[] var4 = SoundCategory.values();

        for (SoundCategory soundCategory : var4) {
            if (soundCategory != SoundCategory.MASTER) {
                this.addDrawableChild(new SoundSliderWidget(this.client, this.width / 2 - 155 + k % 2 * 160, this.height / 6 - 12 + 22 * (k >> 1), soundCategory, 150));
                ++k;
            }
        }

        if (k % 2 == 1) {
            ++k;
        }
        this.addDrawableChild(new SoundSliderWidgetHack(
                this.width / 2 + 5,
                this.height / 6 - 12 + 22 * 5,
                150,
                20,
                new TranslatableText("endermanshut.text.screams", new TranslatableText(endermanVolume != 0 ? (int)(endermanVolume * 100) + "%" : "endermanshut.text.coff")),
                endermanVolume
        ));
        this.addDrawableChild(Option.SUBTITLES.createButton(this.gameOptions, this.width / 2 - 155, this.height / 6 - 12 + 22 * 6, 150));
        this.addDrawableChild(new ButtonWidget(
                this.width / 2 + 5,
                this.height / 6 - 12 + 22 * 6,
                150,
                20,
                new TranslatableText("endermanshut.text.ambient", new TranslatableText(turnOffScreams ? "endermanshut.text.on" : "endermanshut.text.off")),
                click -> {
                    turnOffScreams = !turnOffScreams;
                    this.clearChildren();
                    this.init();
                }));
        this.addDrawableChild(Option.AUDIO_DEVICE.createButton(this.gameOptions, this.width / 2 - 155, this.height / 6 - 12 + 22 * 7, 310));
        //k += 2;

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 - 12 + 22 * 8, 200, 20, ScreenTexts.DONE, (button) -> {
            this.client.setScreen(this.parent);
        }));


    }
}
