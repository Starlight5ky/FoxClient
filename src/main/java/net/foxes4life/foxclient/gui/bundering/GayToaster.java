package net.foxes4life.foxclient.gui.bundering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/*
 * The reason for this class/package name is a joke. Quite literally, it's a joke.
 * Because Bundering is a gay toaster (protogen)
 * so yeah this is a toast -> toaster -> bundering -> gay toaster
 */

@Environment(EnvType.CLIENT)
public class GayToaster implements Toast {
    private final Text title;
    private final Text description;
    private long startTime;
    private boolean justUpdated;

    private static final Identifier bgTex = new Identifier("foxclient", "textures/ui/toasts.png");

    public GayToaster(Text title, @Nullable Text description) {
        this.title = title;
        this.description = description;
    }

    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        if (this.justUpdated) {
            this.startTime = startTime;
            this.justUpdated = false;
        }
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, bgTex);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        manager.getClient().getTextureManager().bindTexture(bgTex);
        manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());

        if (this.description == null) {
            manager.getClient().textRenderer.draw(matrices, this.title, 30.0F, 12.0F, 0xf77622);
        } else {
            manager.getClient().textRenderer.draw(matrices, this.title, 30.0F, 7.0F, 0xf77622);
            manager.getClient().textRenderer.draw(matrices, this.description, 30.0F, 18.0F, 0xffffff);
            RenderSystem.enableBlend();
        }

        return startTime - this.startTime >= 1000L ? Visibility.HIDE : Visibility.SHOW;
    }
}