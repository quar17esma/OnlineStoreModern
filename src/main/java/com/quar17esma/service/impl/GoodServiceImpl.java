package com.quar17esma.service.impl;

import com.quar17esma.dao.GoodRepository;
import com.quar17esma.exceptions.NotEnoughGoodException;
import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.GoodService;
import com.quar17esma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("goodService")
@Transactional
public class GoodServiceImpl extends AbstractPagingAndSortingService<Good> implements GoodService {
    @Autowired
    private GoodRepository repository;
    @Autowired
    private OrderService orderService;

    @Override
    public void addGoodToCart(Order cart, Long goodId, int orderedQuantity) throws NotEnoughGoodException {
        Good good = repository.getOne(goodId);
        int totalQuantity = countTotalOrderedQuantity(cart, orderedQuantity, good);
        checkEnoughGood(totalQuantity, good);
        cart.getOrderedGoods().put(good, totalQuantity);
    }

    private int countTotalOrderedQuantity(Order cart, int orderedQuantity, Good good) {
        int totalQuantity = orderedQuantity;
        if (cart.getOrderedGoods().containsKey(good)) {
            int orderedPrev = cart.getOrderedGoods().get(good);
            totalQuantity = orderedPrev + orderedQuantity;
        }
        return totalQuantity;
    }

    private void checkEnoughGood(int orderedQuantity, Good good) throws NotEnoughGoodException {
        if (isNotEnoughGood(orderedQuantity, good)) {
            throw new NotEnoughGoodException(good.getId());
        }
    }

    private boolean isNotEnoughGood(int orderedQuantity, Good good) {
        if (Integer.compare(good.getQuantity(), orderedQuantity) < 0) {
            return true;
        }

        return false;
    }

    private void writeOffGood(int orderedQuantity, Good good) {
        int diff = good.getQuantity() - orderedQuantity;
        if (diff >= 0) {
            good.setQuantity(diff);
        } else {
            throw new RuntimeException("There is not enough good quantity");
        }
    }

    public List<Good> findByNameContains(String searchString) {
        return repository.findByNameContains(searchString);
    }
}
