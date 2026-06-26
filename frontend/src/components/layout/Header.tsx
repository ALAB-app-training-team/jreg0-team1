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
                className="h-20 cursor-pointer"
                onClick={() => navigate('/')}
            />

            <div className="flex items-center gap-8">
                <button
                    className={`hover:bg-primary/20 flex cursor-pointer gap-2 ${getButtonClass('/')} rounded-lg p-1`}
                    onClick={() => navigate('/')}
                    type="button"
                >
                    <span className="material-symbols-outlined">search</span>
                    新幹線をさがす
                </button>
                <button
                    className={`hover:bg-primary/20 flex cursor-pointer gap-2 ${getButtonClass('/reservationList')} rounded-lg p-1`}
                    onClick={() => navigate('/reservationList')}
                    type="button"
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
