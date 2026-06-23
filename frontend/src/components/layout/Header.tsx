import { useNavigate } from 'react-router-dom';

import logo from '@/assets/logo.png';

function Header() {
    const navigate = useNavigate();
    return (
        <header className="border-b-primary/20 flex h-24 items-center justify-between border-b p-4">
            <img
                src={logo}
                alt="jreg0のロゴ"
                className="h-20"
                onClick={() => navigate('/')}
            />

            <div className="flex gap-8">
                <div className="flex gap-2">
                    <span className="material-symbols-outlined">search</span>
                    新幹線をさがす
                </div>
                <div className="flex gap-2">
                    <span className="material-symbols-outlined">
                        confirmation_number
                    </span>
                    予約確認
                </div>
            </div>
        </header>
    );
}

export default Header;
