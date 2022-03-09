package com.ws.mysololife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ws.mysololife.R
import com.ws.mysololife.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false )
        // Inflate the layout for this fragment
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookMarkFragment)
        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)
        }
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }

        val fortune = mutableListOf<String>()
        fortune.add("어렵지 않게 웃을 수 있는 날이니 하루 종일 행복한 기운의 가운데에 위치할 수 있을 것입니다. 당신이 목표했던 일이 생각보다 쉽게 이루어질 수 있으며 또한 주변 사람들에게 자신의 능력을 인정받거나 소원했던 관계도 희망적으로 전환되는 등 여러 가지로 당신의 운이 가득한 날입니다. 또한 당신의 손길이 닿는 곳 닿는 곳마다 하늘의 도움이 따른다고 표현해야 할 것이지요. 당찬 마음으로 밖으로 나서 보시기 바랍니다. 당신의 행운이 바로 눈 앞에서 당신을 기다리고 있습니다.")
        fortune.add("당신의 일이나 투자를 돕는 결정적인 사람을 만날 수 있을 것이니 가능한 한 많은 사람들을 만나보고 대화를 나누어 보는 것이 이득이 되는 날입니다. 자신 혼자서 결론을 내려고 한다면 생각보다 많은 장애에 부딪혀 버리는 경우도 생길 수 있습니다. 그로 인해 자신감을 상실하거나 투자의 성과를 안 좋은 방향으로 몰고 갈 수 있다는 생각을 또한 해야 할 것입니다. 주변의 말에 귀를 기울이는 태도를 유지하세요. 또한 로또 구매를 할 때에도 다른 사람의 조언에 귀를 기울여 보는 것이 좋겠습니다.")
        fortune.add("정해진 스케줄에 너무 얽매여 있기 보다는 자신 만의 행동 양식과 개성을 살려 나가는 것이 좋은 날입니다. 너무나 틀에 박혀있는 생활태도는 자신에 대한 발전을 전혀 이루지 못하게 만드는 수도 있습니다. 그러므로 새로운 환경을 가끔은 추구할 필요도 있고 자신만의 목표에 맞는 계획을 짜보는 것도 좋습니다. 또한 다른 사람의 말이나 방식에 너무 신경 쓰지 마십시오. 어차피 행복이라는 것이 지극히 주관적인 것이니 말이지요. 자신이 애쓰는 만큼 행복이 가까워져 있습니다.")
        fortune.add("상대방과 약간의 마찰이나 토라짐이 있었다면 근교로 나들이를 가보는 것은 어떨지요. 가끔은 지나치게 서로에게 소홀해져 있는 모습을 하고 있지는 않았나 반성할 수 있는 기회이기도 합니다. 또한 상대방과 자신의 의견차이를 이해하기 보다는 자신의 입장만 고수하려는 이기적인 생각을 하고 있지는 않았는지 반성의 시간을 가져보세요. 또한 굳이 많은 신경을 쓰지 않아도 주변 분위기에 따라 두 사람의 마음도 이전과 같이 평화로워질 것이니 말입니다. 새로운 전환점을 찾아보세요.")
        fortune.add("투자에 있어서 방식을 약간 바꾸는 것도 고려해 봄직한 일입니다. 항상 똑같은 방식으로 투자를 하다 보면 일의 효율성을 잃게 되는 경우도 생길 수 있습니다. 자신이 생각하는 것만큼 좋은 결과를 항상 얻을 수 없듯이 발전적인 태도를 갖지 않고 현실에 만족하는 태도로는 결코 좋은 성과와 연결될 수 없다는 것을 명심하도록 하세요. 틀에 박힌 형태보다는 다른 사람이 미쳐 발견해내지 못한 구석에 집중해 보십시오. 로또를 구매할 때도 특이한 조합을 사용해 보는 것은 어떨까요.")
        fortune.add("가능성과 확률이라는 요소에 평소보다 많은 신경을 쓸 필요가 있는 날입니다. 투자와 노력 만으로 100% 성공을 보장할 수는 없을 것이니 말이지요. 아무리 열심히 노력하고 일해도 안 되는 일은 계속 안 되기 마련입니다. 따라서 방향 설정에 각별히 신경을 쓸 수 있도록 하십시오. 멀리 치고 나가 보아야 엉뚱한 방향이라면 손해 만이 있을 뿐이지요. 오늘은 업무량이 다소 많은 편이라 쉽게 지칠 수도 있지만 그만큼 또 쉽게 회복할 수도 있을 것입니다. 지금 한 번 더 참고 견디면 추후에 열 번 잘했다고 생각하게 될 것이니 조금만 더 힘을 내볼 수 있도록 하십시오. 그 밖에도 직장 동료와 원만하게 지낼 수 있을 것이며 상사의 잔소리 또한 많지 않은 날입니다. 이런 점들을 잘 염두에 두시고 하루를 시작해 보도록 하십시오.")

        binding.fortuneContent.setText(fortune.random())
        return binding.root
    }

}