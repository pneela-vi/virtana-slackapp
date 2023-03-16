package com.virtana.slackapp;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;

import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.TextObject;


import java.util.ArrayList;
import java.util.List;

public class HelpBlockBuilder {

    public List<LayoutBlock> handleHelpCommand(SlashCommandRequest req, SlashCommandContext ctx) {
        // Build the message blocks
        List<LayoutBlock> blocks = new ArrayList();

        // Section block for the help message
        TextObject sectionText = MarkdownTextObject.builder()
                .text("*Virtana Platform Slash Commands Help*\nUse these slash commands to get reports.")
                .build();
        SectionBlock sectionBlock = SectionBlock.builder()
                .text(sectionText)
                .build();

        blocks.add(sectionBlock);


        // Divider block to separate the help message from the commands
        DividerBlock dividerBlock = DividerBlock.builder()
                .build();
        blocks.add(dividerBlock);

        // Section block for the '/virtana-platform show ipm event viewer' command
        TextObject ipmText = MarkdownTextObject.builder()
                .text("`/virtana-platform show ipm event viewer `- Infrastructure Performance Management dashboard")
                .build();
        SectionBlock ipmBlock = SectionBlock.builder()
                .text(ipmText)
                .build();
        blocks.add(ipmBlock);

        // Section block for the '/virtana-platform show usersbyorg [limit]' command
        TextObject userOrgText = MarkdownTextObject.builder()
                .text("`/virtana-platform show usersbyorg [limit]` - List users by org using limit options")
                .build();
        SectionBlock userOrgBlock = SectionBlock.builder()
                .text(userOrgText)
                .build();
        blocks.add(userOrgBlock);

        // Section block for the '/virtana-platform show idle resources [limit]' command
        TextObject idleText = MarkdownTextObject.builder()
                .text("`/virtana-platform show idle resources [limit]` - List idle resources using limit options")
                .build();
        SectionBlock idleBlock = SectionBlock.builder()
                .text(idleText)
                .build();
        blocks.add(idleBlock);

        // Section block for the '/virtana-platform show right sizing policy' command
        TextObject policyText = MarkdownTextObject.builder()
                .text("`/virtana-platform show right sizing policy` - List right sizing policy dashboard")
                .build();
        SectionBlock policyBlock = SectionBlock.builder()
                .text(policyText)
                .build();
        blocks.add(policyBlock);

        return blocks;

//        // Build the message object
//        com.slack.api.model.Message message = com.slack.api.model.Message.builder()
//                .blocks(blocks)
//                .build();
//
//        // Respond with the message
//        return ctx.ack(message);
    }
}