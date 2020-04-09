package untils;

import org.springframework.stereotype.Component;

import org.modelmapper.ModelMapper;
@Component
public class BeanTools {
    private ModelMapper modelMapper;

    public BeanTools() {
        modelMapper = new ModelMapper();
    }

    public <M, E> E map(M source, E target) {
        modelMapper.map(source, target);
        return target;
    }
}
