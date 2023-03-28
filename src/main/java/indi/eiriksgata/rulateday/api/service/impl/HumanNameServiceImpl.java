package indi.eiriksgata.rulateday.api.service.impl;

import indi.eiriksgata.rulateday.mapper.NamesCorpusMapper;
import indi.eiriksgata.rulateday.api.service.HumanNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class HumanNameServiceImpl implements HumanNameService {

    @Autowired
    NamesCorpusMapper namesCorpusMapper;

    @Override
    public String randomName(int number) {
        StringBuilder resultNames = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int count = 0; count < number; count++) {
            int randomValue = random.nextInt(3);
            switch (randomValue) {
                case 0:
                    resultNames.append(namesCorpusMapper.getCNAncientRandomName());
                    break;
                case 1:
                    resultNames.append(namesCorpusMapper.getEnglishRandomName());
                    break;
                case 2:
                    resultNames.append(namesCorpusMapper.getJapaneseRandomName());
                    break;
            }
            if (count != number - 1) {
                resultNames.append("、");
            }
        }
        return resultNames.toString();
    }

}
