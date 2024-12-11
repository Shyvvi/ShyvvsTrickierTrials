package net.shyvv.shyvvtrials.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record LeveledKeyComponent(int level, boolean showInTooltip) implements TooltipAppender {
    public static final Codec<LeveledKeyComponent> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("level").forGetter(LeveledKeyComponent::level), Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(LeveledKeyComponent::showInTooltip)).apply(instance, LeveledKeyComponent::new);
    });
    public static final PacketCodec<ByteBuf, LeveledKeyComponent> PACKET_CODEC;

    private static final Text TOOLTIP_TEXT;

    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
//        tooltip.accept(Text.literal("test"));
//        tooltip.accept(Text.literal(TOOLTIP_TEXT.toString()+" : "+this.level));
        tooltip.accept(Text.translatable("item.keylevel").formatted(new Formatting[]{Formatting.GRAY}));
    }

    public int getLevel() {
        return this.level;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, LeveledKeyComponent::level, PacketCodecs.BOOL, LeveledKeyComponent::showInTooltip, LeveledKeyComponent::new);
        TOOLTIP_TEXT = Text.translatable("item.keylevel").formatted(Formatting.GRAY);
    }
}
