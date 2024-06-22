package com.namomi.blog.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namomi.blog.config.error.ErrorCode;
import com.namomi.blog.domain.Article;
import com.namomi.blog.domain.User;
import com.namomi.blog.dto.AddArticleRequest;
import com.namomi.blog.dto.UpdateArticleRequest;
import com.namomi.blog.repository.BlogRepository;
import com.namomi.blog.repository.UserRepository;
import com.namomi.blog.service.BlogService;

/**
 * Class: BaseEntity Project: com.minizin.travel.blog.controller
 * <p>
 * Description: BlogApiControllerTest
 *
 * @author JANG CHIHUN
 * @date 6/9/24 14:53 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    User user;



    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        blogRepository.deleteAll();
    }

    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
            .email("user@gmail.com")
            .password("test")
            .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
            new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @Test
    void addArticleTest() throws Exception {
        // given
        String url = "/api/articles";
        String title = "title";
        String content = "content";
        AddArticleRequest addArticleRequest = new AddArticleRequest(title, content);

        String requestBody = objectMapper.writeValueAsString(addArticleRequest);

        Principal principal = mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");


        // when
        ResultActions result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .principal(principal)
            .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

    }

    @Test
    void findAllArticlesTest() throws Exception {
        // given
        String url = "/api/articles";
        Article savedArticle = createDefaultArticle();


        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()))
            .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()));
    }

    @Test
    void findArticleTest() throws Exception {
        //given
        String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
            .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
    }

    @Test
    void updateArticleTest() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);


        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    @Test
    void deleteArticleTest() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
            .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }


    @Test
    public void invalidHttpMethod() throws Exception{
        //given
        final String url = "/api/articles/{id}";

        //when
        ResultActions resultActions = mockMvc.perform(post(url, 1));

        //then
        resultActions.andDo(print())
            .andExpect(status().isMethodNotAllowed())
            .andExpect(jsonPath("$.message").value(ErrorCode.METHOD_NOT_ALLOWED.getMessage()));

    }

    @Test
    public void findArticleInvalidArticle() throws Exception{
        //given
        final String url = "/api/articles/{id}";
        final long invalidId = 1;

        //when
        ResultActions resultActions = mockMvc.perform(get(url, invalidId));

        //then
        resultActions
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(ErrorCode.ARTICLE_NOT_FOUND.getCode()));
    }

    private Article createDefaultArticle() {
        return blogRepository.save(Article.builder()
            .title("title")
            .author(user.getUsername())
            .content("content")
            .build());
    }
}
