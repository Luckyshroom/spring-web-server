package io.depa.service;

import io.depa.exception.ResourceNotFoundException;
import io.depa.model.*;
import io.depa.repository.*;
import io.depa.payload.request.PostRequest;
import io.depa.payload.response.PagedResponse;
import io.depa.payload.response.PostResponse;
import io.depa.util.Helpers;
import io.depa.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for {@link Post}.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Service
@Transactional
public class PostService {
    private final CategoryRepository categoryRepository;
    private final ContextRepository contextRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(CategoryRepository categoryRepository,
                       ContextRepository contextRepository,
                       PostRepository postRepository,
                       TagRepository tagRepository,
                       UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.contextRepository = contextRepository;
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    public Post createByCategory(int categoryId, PostRequest postRequest) {
        Post post = new Post(
                postRequest.getImage(),
                postRequest.getTitle(),
                postRequest.getContent()
        );

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Tag> tags = postRequest.getTags().parallelStream().map(name -> {
            Tag temp = tagRepository.findByName(name);
            return temp == null ? new Tag(name) : temp;
        }).collect(Collectors.toList());

        if (postRequest.getContext() != null) {
            // Looking for a tag by context
            Tag tag = tagRepository.findByName(postRequest.getContext());
            Context context = tag == null ? new Context(postRequest.getContext()) : contextRepository.findByTag(tag);
            post.setContext(context);
            tags.add(context.getTag());
        }

        post.setCategory(category);
        post.setTags(tags);

        return postRepository.save(post);
    }

    public PagedResponse<PostResponse> getByCategory(int categoryId, int page, int size) {
        Helpers.validatePageNumberAndSize(page, size);
        // Retrieve Posts
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Page<Post> posts = postRepository.findByCategory(category, pageable);

        if (posts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), posts.getNumber(),
                    posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
        }
        // Map Posts to PostResponses containing post creator details
        Map<Integer, User> creatorMap = Helpers.getCreatorMap(posts.getContent(), userRepository);

        List<PostResponse> postResponses = posts.map(post -> ModelMapper
                .toPostResponse(post, creatorMap.get(post.getCreatedBy()))).getContent();

        return new PagedResponse<>(postResponses, posts.getNumber(),
                posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
    }

    public PagedResponse<PostResponse> getByTag(int tagId, int page, int size) {
        Helpers.validatePageNumberAndSize(page, size);
        // Retrieve Posts
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        Page<Post> posts = postRepository.findByTags(tag, pageable);

        if (posts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), posts.getNumber(),
                    posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
        }
        // Map Posts to PostResponses containing post creator details
        Map<Integer, User> creatorMap = Helpers.getCreatorMap(posts.getContent(), userRepository);

        List<PostResponse> postResponses = posts.map(post -> ModelMapper
                .toPostResponse(post, creatorMap.get(post.getCreatedBy()))).getContent();

        return new PagedResponse<>(postResponses, posts.getNumber(),
                posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
    }

    public Post getById(int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    public PostResponse getByUrlName(String urlName) {
        // Retrieve Post
        Post post = postRepository.findByUrlName(urlName)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "urlName", urlName));
        // Map Post to PostResponse containing post creator details
        Map<Integer, User> creatorMap = Helpers.getCreatorMap(Collections.singletonList(post), userRepository);

        return ModelMapper.toPostResponse(post, creatorMap.get(post.getCreatedBy()));
    }

    public void save(Post post) {
        postRepository.save(post);
    }
}
