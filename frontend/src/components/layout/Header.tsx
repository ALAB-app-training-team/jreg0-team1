import { useLocation, useNavigate } from 'react-router-dom';

import logo from '@/assets/logo.png';

function Header() {
    const navigate = useNavigate();
    const location = useLocation();
    console.log(location);

    const getButtonClass = (page: string) => {
        return location.pathname === page ? 'contained_btn' : '';
    };
    return (
        <header className="border-b-primary/20 flex h-24 items-center justify-between border-b p-4">
            <img
                src={logo}
                alt="jreg0のロゴ"
                className="h-20"
                onClick={() => navigate('/')}
            />

            <div className="flex items-center gap-8">
                <button
                    className={`flex gap-2 ${getButtonClass('/')} p-1`}
                    onClick={() => navigate('/')}
                >
                    <span className="material-symbols-outlined">search</span>
                    新幹線をさがす
                </button>
                <button
                    className={`flex gap-2 ${getButtonClass('/details/019ef75a-a6f0-798b-88cf-b1db9a320103')} p-1`}
                    onClick={() =>
                        navigate(
                            '/details/019ef75a-a6f0-798b-88cf-b1db9a320103',
                        )
                    }
                >
                    <span className="material-symbols-outlined">
                        confirmation_number
                    </span>
                    予約確認
                </button>
            </div>
        </header>
    );
}

export default Header;
