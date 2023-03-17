package com.virtana.slackapp;

import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.response.ResponseTypes;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.BlockElements;
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewTitle;
import com.slack.api.util.json.GsonFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TabularBlock {
    public Response buildTabularBlock(BlockActionRequest request) {
        String jsonData = "[{\"email\":\"baig.asadulla@virtana.com\",\"name\":\"Baig Asadulla\",\"roles\":[{\"friendlyName\":\"Virtana Developer\"}]},{\"email\":\"polina.tolstikova@virtana.com\",\"name\":\"Polina Tolstikova\",\"roles\":[{\"friendlyName\":\"Virtana Developer\"}]}]";
        List<User> users = Arrays.asList(GsonFactory.createSnakeCase().fromJson(jsonData, User[].class));

//        List<LayoutBlock> blocks = new ArrayList<>();
//
//        // Header Row
//        List<String> headers = Arrays.asList("Email", "Name", "Roles");
//        blocks.add(LayoutBlock.section(section -> section.text(
//                BlockCompositions.markdownText("*" + String.join(" | ", headers) + "*"))));
//
//        // Rows
//        users.forEach(user -> {
//            List<String> values = Arrays.asList(user.getEmail(), user.getName(), user.getRoles().stream()
//                    .map(role -> role.getFriendlyName())
//                    .collect(Collectors.joining(", ")));
//
//            blocks.add(LayoutBlock.section(section -> section.text(
//                    BlockCompositions.markdownText(String.join(" | ", values)))));
//        });
//
//        View view = new View().type(View.Type.MODAL).callbackId("tabular-block");
//        view.blocks(blocks);
        List<LayoutBlock> blocks = new ArrayList<>();

// Add header section
        blocks.add(SectionBlock.builder()
                .text(MarkdownTextObject.builder()
                        .text("*User List*\n")
                        .build())
                .build());

// Add table header row
        blocks.add(SectionBlock.builder()
                .fields(Arrays.asList(
                        MarkdownTextObject.builder()
                                .text("*Email*\n")
                                .build(),
                        MarkdownTextObject.builder()
                                .text("*Name*\n")
                                .build(),
                        MarkdownTextObject.builder()
                                .text("*Role*\n")
                                .build()))
                .build());

// Add user rows
        for (User user : users) {
            String email = (String) user.getEmail();
            String name = (String) user.getName();
            List<Role> roles =  user.roles;
            String role = roles.get(0).getFriendlyName();

            blocks.add(SectionBlock.builder()
                    .fields(Arrays.asList(
                            MarkdownTextObject.builder()
                                    .text(email + "\n")
                                    .build(),
                            MarkdownTextObject.builder()
                                    .text(name + "\n")
                                    .build(),
                            MarkdownTextObject.builder()
                                    .text(role + "\n")
                                    .build()))
                    .build());
        }
        return Response.ok();
    }

    static class User {
        private String email;
        private String name;
        private List<Role> roles;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Role> getRoles() {
            return roles;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }
    }

    static class Role {
        private String friendlyName;

        public String getFriendlyName() {
            return friendlyName;
        }

        public void setFriendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
        }
    }
}

 public class UsersByOrgBuilder {
    public Response usersByOrgBuilder(SlashCommandRequest req ) {
        List<User> users = Arrays.asList(); // Method to parse the JSON data into a List of User objects

        List<LayoutBlock> blocks = new ArrayList<>();

        blocks.add(SectionBlock.builder()
                .text(PlainTextObject.builder()
                        .text("User Information")
                        .build())
                .build());

        blocks.add(DividerBlock.builder().build());

        for (User user : users) {
            blocks.add(SectionBlock.builder()
                    .text(PlainTextObject.builder()
                            .text("*" + user.getName() + "*\nEmail: " + user.getEmail() + "\nRole: " + user.getRoles().get(0).getFriendlyName())
                            .build())
                    .build());
            blocks.add(DividerBlock.builder().build());
        }

        View view = View.builder()
                .type("modal")
                .callbackId("user-info-modal")
                .title(ViewTitle.builder().text("User Information").build())
                .blocks(blocks)
                .build();

        return Response.json(Integer.valueOf(200), view);
//        return Response.builder()
//                .responseAction("open_view")
//                .view(view)
//                .build();
    }
}

class User {
    private String email;
    private String name;
    private List<Role> roles;

    public User(String email, String name, List<Role> roles) {
        this.email = email;
        this.name = name;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<Role> getRoles() {
        return roles;
    }
}

class Role {
    private String friendlyName;

    public Role(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}

