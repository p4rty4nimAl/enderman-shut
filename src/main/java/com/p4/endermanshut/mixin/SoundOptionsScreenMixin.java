package com.p4.endermanshut.mixin;

import com.p4.endermanshut.hack.SoundSliderWidgetHack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SoundSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.p4.endermanshut.ConfigInit.endermanVolume;
import static com.p4.endermanshut.ConfigInit.turnOffScreams;

@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin extends GameOptionsScreen {
    @Nullable
    private ClickableWidget directionalAudioButton;

    public SoundOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }
    @Inject(method = "init()V", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.clearChildren();
        int i = this.height / 6 - 12;
        int k = 0;
        this.addDrawableChild(new SoundSliderWidget(this.client, this.width / 2 - 155 + k % 2 * 160, i + 22 * (k >> 1), SoundCategory.MASTER, 310));
        k += 2;
        SoundCategory[] var4 = SoundCategory.values();
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            SoundCategory soundCategory = var4[var6];
            if (soundCategory != SoundCategory.MASTER) {
                this.addDrawableChild(new SoundSliderWidget(this.client, this.width / 2 - 155 + k % 2 * 160, i + 22 * (k >> 1), soundCategory, 150));
                ++k;
            }
        }

        if (k % 2 == 1) {
            ++k;
        }

        this.addDrawableChild(this.gameOptions.getSoundDevice().createButton(this.gameOptions, this.width / 2 - 155, i + 22 * (k >> 1), 150));
        k += 2;
        this.addDrawableChild(this.gameOptions.getShowSubtitles().createButton(this.gameOptions, this.width / 2 - 155, i + 22 * (k >> 1), 150));
        this.directionalAudioButton = this.gameOptions.getDirectionalAudio().createButton(this.gameOptions, this.width / 2 + 5, i + 22 * (k >> 1), 150);
        this.addDrawableChild(this.directionalAudioButton);
        k += 2;
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, i + 22 * (k >> 1), 200, 20, ScreenTexts.DONE, (button) -> {
            this.client.setScreen(this.parent);
        }));
        this.addDrawableChild(new SoundSliderWidgetHack(
                this.width / 2 + 5,
                this.height / 6 - 12 + 22 * 5,
                150,
                20,
                Text.translatable("endermanshut.text.screams", Text.translatable(endermanVolume != 0 ? (int)(endermanVolume * 100) + "%" : "endermanshut.text.coff")),
                endermanVolume
        ));
        this.addDrawableChild(new ButtonWidget(
                this.width / 2 + 5,
                this.height / 6 - 12 + 22 * 6,
                150,
                20,
                Text.translatable("endermanshut.text.ambient", Text.translatable(turnOffScreams ? "endermanshut.text.on" : "endermanshut.text.off")),
                click -> {
                    turnOffScreams = !turnOffScreams;
                    this.clearChildren();
                    this.init();
                }));
    }
}
